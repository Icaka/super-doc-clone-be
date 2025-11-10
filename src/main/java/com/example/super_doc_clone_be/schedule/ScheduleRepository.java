package com.example.super_doc_clone_be.schedule;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Integer> {
    List<Schedule> findByDoctorId(Integer id);

    Schedule findByDateAndDoctor_Id(LocalDate date, Integer doctorId);
}
