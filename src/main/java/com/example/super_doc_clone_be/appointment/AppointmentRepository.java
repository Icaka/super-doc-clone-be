package com.example.super_doc_clone_be.appointment;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {
    @Query("SELECT a FROM Appointment a WHERE a.doctor_id.id = :id")
    List<Appointment> findByDoctor_id(Integer id);
}
