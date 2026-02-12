package com.example.super_doc_clone_be.appointment;

import com.example.super_doc_clone_be.appointment.dtos.AppointmentDTO;
import com.example.super_doc_clone_be.appointment.dtos.CreateAppointmentDTO;
import com.example.super_doc_clone_be.doctors.DoctorRepository;
import com.example.super_doc_clone_be.security.CurrentUserService;
import com.example.super_doc_clone_be.user.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    public List<AppointmentDTO> toAppointmentDTO(List<Appointment> appointments) {
        List<AppointmentDTO> result = new ArrayList<>();
        for (Appointment a : appointments) {
            result.add(new AppointmentDTO(a.getId(), a.getDate(), a.getSlot(), a.getUser().getId()));
        }
        return result;
    }

    public boolean create(final Integer doctorId, final CreateAppointmentDTO appointmentDTO) {
        User userFromToken = currentUserService.get();
        Appointment temp = new Appointment();
        temp.setDate(appointmentDTO.date());
        temp.setSlot(appointmentDTO.slot());
        temp.setDoctor(doctorRepository.findById(doctorId).orElseThrow());
        temp.setUser(userFromToken);
        this.appointmentRepository.save(temp);
        return true;
    }
}
