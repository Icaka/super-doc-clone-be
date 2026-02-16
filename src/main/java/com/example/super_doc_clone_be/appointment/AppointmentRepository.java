package com.example.super_doc_clone_be.appointment;


import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {
    List<Appointment> findByDoctorId(Integer id);

    List<Appointment> findByDoctorIdAndDate(Integer id, LocalDate date);

    List<Appointment> findByDoctorIdAndUserId(Integer doctorId, Integer userId);

    List<Appointment> findByDoctorIdAndDateAndSlot(Integer doctorId, LocalDate date, Integer slot);
}
