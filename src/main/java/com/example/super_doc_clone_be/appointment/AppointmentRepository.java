package com.example.super_doc_clone_be.appointment;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {
    //@Query("SELECT a FROM Appointment a WHERE a.doctor.id = :id")
    List<Appointment> findByDoctorId(Integer id);
}
