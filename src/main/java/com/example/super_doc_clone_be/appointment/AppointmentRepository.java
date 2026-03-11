package com.example.super_doc_clone_be.appointment;


import com.example.super_doc_clone_be.schedule.slot.Slot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {
    List<Appointment> findByDoctorId(Integer id);

    List<Appointment> findByDoctorIdAndDate(Integer id, LocalDate date);

    List<Appointment> findByDoctorIdAndUserId(Integer doctorId, Integer userId);

//    List<Appointment> findByDoctorIdAndDateAndSlot(Integer doctorId, LocalDate date, Integer slot);

//    boolean existsByDoctorIdAndDateAndSlotAndStatusNot(
//            Integer doctorId,
//            LocalDate date,
//            Integer slot,
//            AppointmentStatus status
//    );

//    boolean existsBySlot_Id(Integer slotId);

    List<Appointment> findBySlot_Id(Integer slotId);

    List<Appointment> findBySlot(Slot slot);

    List<Appointment> findByUserId(Integer userId);
}
