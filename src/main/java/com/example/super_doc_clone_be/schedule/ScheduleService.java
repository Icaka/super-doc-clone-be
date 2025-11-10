package com.example.super_doc_clone_be.schedule;

import com.example.super_doc_clone_be.schedule.dtos.ScheduleDTO;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;

    ScheduleService(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    public ScheduleDTO findByDateAndDoctor(LocalDate date, Integer doctorId) {
        Schedule temp = scheduleRepository.findByDateAndDoctor_Id(date, doctorId);
        return new ScheduleDTO(temp.getId(), temp.getSlotCount(), temp.getSlotLength(), temp.getWorkStart(), temp.getDate());
    }
}