package com.example.super_doc_clone_be.appointment;

import com.example.super_doc_clone_be.appointment.dtos.AppointmentDTO;
import com.example.super_doc_clone_be.appointment.dtos.CreateAppointmentDTO;
import com.example.super_doc_clone_be.doctors.DoctorRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final DoctorRepository doctorRepository;

    AppointmentService(AppointmentRepository appointmentRepository, DoctorRepository doctorRepository) {
        this.appointmentRepository = appointmentRepository;
        this.doctorRepository = doctorRepository;
    }

    public List<AppointmentDTO> findByDoctorId(final Integer doctorId) {
        List<Appointment> appointments = appointmentRepository.findByDoctorId(doctorId);
        List<AppointmentDTO> result = new ArrayList<>();
        for (Appointment a : appointments) {
            result.add(new AppointmentDTO(a.getId(), a.getDate(), a.getSlot(), a.getTaken()));
        }
        return result;
    }

    public boolean create(final Integer doctorId, final CreateAppointmentDTO appointmentDTO) {
        Appointment temp = new Appointment();
        temp.setDate(appointmentDTO.date());
        temp.setSlot(appointmentDTO.slot());
        temp.setTaken(appointmentDTO.taken());
        temp.setDoctor(doctorRepository.findById(doctorId).orElseThrow());
        this.appointmentRepository.save(temp);
        return true;
    }
}
