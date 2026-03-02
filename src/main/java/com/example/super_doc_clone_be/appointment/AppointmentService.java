package com.example.super_doc_clone_be.appointment;

import com.example.super_doc_clone_be.appointment.dtos.AppointmentDTO;
import com.example.super_doc_clone_be.appointment.dtos.CreateAppointmentDTO;
import com.example.super_doc_clone_be.doctors.DoctorRepository;
import com.example.super_doc_clone_be.schedule.slot.Slot;
import com.example.super_doc_clone_be.schedule.slot.SlotRepository;
import com.example.super_doc_clone_be.schedule.slot.SlotStatus;
import com.example.super_doc_clone_be.security.CurrentUserService;
import com.example.super_doc_clone_be.user.User;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final DoctorRepository doctorRepository;
    private final SlotRepository slotRepository;
    private final CurrentUserService currentUserService;

    AppointmentService(AppointmentRepository appointmentRepository, DoctorRepository doctorRepository, SlotRepository slotRepository, CurrentUserService currentUserService) {
        this.appointmentRepository = appointmentRepository;
        this.doctorRepository = doctorRepository;
        this.slotRepository = slotRepository;
        this.currentUserService = currentUserService;
    }

    public List<AppointmentDTO> findByDoctorId(final Integer doctorId) {
        List<Appointment> appointments = appointmentRepository.findByDoctorId(doctorId);
        return toAppointmentDTO(appointments);
    }

    public List<AppointmentDTO> findByDoctorAndUserId(final Integer doctorId, final Integer userId) {
        List<Appointment> appointments = appointmentRepository.findByDoctorIdAndUserId(doctorId, userId);
        return toAppointmentDTO(appointments);
    }

    public List<AppointmentDTO> findByUserId(final Integer userId) {
        List<Appointment> appointments = appointmentRepository.findByUserId(userId);
        return toAppointmentDTO(appointments);
    }

    public List<AppointmentDTO> toAppointmentDTO(List<Appointment> appointments) {
        List<AppointmentDTO> result = new ArrayList<>();
        for (Appointment a : appointments) {
            result.add(new AppointmentDTO(a.getId(), a.getDate(), a.getSlot().getNumber(), a.getUser().getId(), a.getStatus()));
        }
        return result;
    }

    public boolean create(final Integer doctorId, final CreateAppointmentDTO appointmentDTO) {
        Slot tempSlot = slotRepository.findById(appointmentDTO.slotId())
                .orElseThrow(() -> new RuntimeException("Slot doesn't exist in db"));
        if (tempSlot.getStatus() != SlotStatus.AVAILABLE) {
            throw new RuntimeException("Slot already booked");
        }
        if (tempSlot.getSchedule().getDate().isBefore(LocalDate.now())) {
            throw new RuntimeException("Cannot book in the past");
        }
        User userFromToken = currentUserService.get();
        Appointment temp = new Appointment();
        temp.setDate(tempSlot.getSchedule().getDate());
        temp.setSlot(tempSlot); //TODO
        temp.setDoctor(doctorRepository.findById(doctorId).orElseThrow());
        temp.setUser(userFromToken);
        temp.setStatus(AppointmentStatus.PENDING);

        //tempSlot.setStatus(SlotStatus.UNAVAILABLE);//TODO

        this.appointmentRepository.save(temp);
        return true;
    }

    public boolean cancel(final Integer doctorId, final CreateAppointmentDTO appointmentDTO) {
        if (appointmentRepository.existsBySlot_Id(appointmentDTO.slotId())) {
            throw new RuntimeException("Appointment doesn't exist");
        }
        this.freeSlot(appointmentRepository.findBySlot_Id(appointmentDTO.slotId()).getFirst().getSlot());
        appointmentRepository.deleteById(
                appointmentRepository.findBySlot_Id(appointmentDTO.slotId()).getFirst().getId());
        return true;
    }

    public boolean adminDeleteById(final Integer appointmentId) {
        if (!Objects.equals(currentUserService.getRole(), "ROLE_ADMIN")) {
            throw new RuntimeException("No Admin rights");
        }
        if (appointmentRepository.findById(appointmentId).isEmpty()) {
            throw new RuntimeException("Appointment with that id doesn't exist");
        }
        appointmentRepository.deleteById(appointmentId);
        return true;
    }

    List<AppointmentDTO> getLoggedUserAppointments() {
        return findByUserId(this.currentUserService.getId());
    }

    public boolean userCancelById(final Integer appointmentId) {
        Appointment temp = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Appointment with that id doesn't exist"));
        if (!Objects.equals(temp.getUser().getId(), currentUserService.getId())) {
            throw new RuntimeException("This Appointment doesn't belong to you");
        }
        temp.setStatus(AppointmentStatus.CANCELLED);
        this.freeSlot(temp.getSlot());
        appointmentRepository.save(temp);
        return true;
    }

    public boolean changeAppointmentStatus(final Integer id, AppointmentStatus status) {
        Appointment temp = appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment with that id doesn't exist"));
        if (!Objects.equals(temp.getDoctor().getId(), this.currentUserService.getId())) {
            throw new RuntimeException("This Appointment is not scheduled with this doctor");
        }
        if (!validateTransition(temp.getStatus(), status)) {
            throw new RuntimeException("Can't change status this way");
        }
        temp.setStatus(status);
        this.appointmentRepository.save(temp);
        return true;
    }

    public boolean validateTransition(final AppointmentStatus oldStatus, final AppointmentStatus newStatus) {
        if (oldStatus == AppointmentStatus.PENDING) {
            if (newStatus == AppointmentStatus.CONFIRMED || newStatus == AppointmentStatus.CANCELLED) {
                return true;
            }
        }
        if (oldStatus == AppointmentStatus.CONFIRMED) {
            return newStatus == AppointmentStatus.COMPLETED || newStatus == AppointmentStatus.CANCELLED || newStatus == AppointmentStatus.NO_SHOW;
        }
        return false;
    }

    public void freeSlot(final Slot slot) {
        slot.setStatus(SlotStatus.AVAILABLE);
        this.slotRepository.save(slot);
    }

    public boolean acceptAppointment(Integer appointmentId) {
        Appointment temp = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Appointment with that id doesn't exist"));
        if (temp.getStatus() != AppointmentStatus.PENDING) {
            return false;
        }
        if (temp.getSlot().getStatus() == SlotStatus.AVAILABLE) {
            temp.setStatus(AppointmentStatus.CONFIRMED);
            temp.getSlot().setStatus(SlotStatus.BOOKED);
            this.slotRepository.save(temp.getSlot());
        } else {
            throw new RuntimeException("Slot is booked");
        }
        return true;
    }
}
