package com.example.super_doc_clone_be.appointment;

import com.example.super_doc_clone_be.appointment.dtos.CreateAppointmentDTO;
import com.example.super_doc_clone_be.doctors.DoctorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final DoctorRepository doctorRepository;

    AppointmentService(AppointmentRepository appointmentRepository, DoctorRepository doctorRepository) {
        this.appointmentRepository = appointmentRepository;
        this.doctorRepository = doctorRepository;
    }

    public Appointment findById(Integer id){
        return this.appointmentRepository.findById(id).orElseThrow();
    }

    public List<Appointment> findByDoctorId(final Integer doctorId){
        return this.appointmentRepository.findByDoctor_id(doctorId);
    }

    public boolean create(final Integer doctorId, final CreateAppointmentDTO appointmentDTO) {
        Appointment temp = new Appointment();
        temp.setDate(appointmentDTO.date());
        temp.setSlot(appointmentDTO.slot());
        temp.setDoctor_id(doctorRepository.findById(doctorId).orElseThrow());
        this.appointmentRepository.save(temp);
        return true;
    }
}
