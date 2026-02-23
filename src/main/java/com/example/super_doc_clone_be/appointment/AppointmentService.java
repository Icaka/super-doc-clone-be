package com.example.super_doc_clone_be.appointment;

import com.example.super_doc_clone_be.appointment.dtos.AppointmentDTO;
import com.example.super_doc_clone_be.appointment.dtos.CreateAppointmentDTO;
import com.example.super_doc_clone_be.doctors.DoctorRepository;
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
    private final CurrentUserService currentUserService;

    AppointmentService(AppointmentRepository appointmentRepository, DoctorRepository doctorRepository, CurrentUserService currentUserService) {
        this.appointmentRepository = appointmentRepository;
        this.doctorRepository = doctorRepository;
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
            result.add(new AppointmentDTO(a.getId(), a.getDate(), a.getSlot(), a.getUser().getId(), a.getStatus()));
        }
        return result;
    }

    public boolean create(final Integer doctorId, final CreateAppointmentDTO appointmentDTO) {
        if (appointmentRepository.existsByDoctorIdAndDateAndSlotAndStatusNot(doctorId, appointmentDTO.date(), appointmentDTO.slot(), AppointmentStatus.CANCELLED)) {
            throw new RuntimeException("Slot already booked");
        }
        if (appointmentDTO.date().isBefore(LocalDate.now())) {
            throw new RuntimeException("Cannot book in the past");
        }
        User userFromToken = currentUserService.get();
        Appointment temp = new Appointment();
        temp.setDate(appointmentDTO.date());
        temp.setSlot(appointmentDTO.slot());
        temp.setDoctor(doctorRepository.findById(doctorId).orElseThrow());
        temp.setUser(userFromToken);
        temp.setStatus(AppointmentStatus.PENDING);
        this.appointmentRepository.save(temp);
        return true;
    }

    public boolean cancel(final Integer doctorId, final CreateAppointmentDTO appointmentDTO) {
        if (appointmentRepository.findByDoctorIdAndDateAndSlot(doctorId, appointmentDTO.date(), appointmentDTO.slot()).isEmpty()) {
            throw new RuntimeException("Appointment doesn't exist");
        }
        appointmentRepository.deleteById(
                appointmentRepository.findByDoctorIdAndDateAndSlot(doctorId, appointmentDTO.date(), appointmentDTO.slot()).getFirst().getId());
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
        appointmentRepository.save(temp);
        return true;
    }
}
