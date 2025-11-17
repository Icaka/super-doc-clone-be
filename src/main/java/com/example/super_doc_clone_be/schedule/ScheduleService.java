package com.example.super_doc_clone_be.schedule;

import com.example.super_doc_clone_be.doctors.DoctorRepository;
import com.example.super_doc_clone_be.schedule.dtos.CreateScheduleDTO;
import com.example.super_doc_clone_be.schedule.dtos.ScheduleDTO;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final DoctorRepository doctorRepository;

    ScheduleService(ScheduleRepository scheduleRepository, DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
        this.scheduleRepository = scheduleRepository;
    }

    public ScheduleDTO findByDateAndDoctor(LocalDate date, Integer doctorId) {
        Schedule temp = scheduleRepository.findByDateAndDoctor_Id(date, doctorId);
        return new ScheduleDTO(temp.getId(), temp.getSlotCount(), temp.getSlotLength(), temp.getWorkStart(), temp.getDate());
    }

    boolean create(Integer doctorId, CreateScheduleDTO createReviewDTO) {
        Schedule temp = new Schedule();
        temp.setDoctor(doctorRepository.findById(doctorId).orElseThrow());
        temp.setSlotCount(createReviewDTO.count());
        temp.setSlotLength(createReviewDTO.length());
        temp.setWorkStart(createReviewDTO.start());
        temp.setDate(createReviewDTO.date());
        this.scheduleRepository.save(temp);
        return true;
    }
}