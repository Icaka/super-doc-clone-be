package com.example.super_doc_clone_be.schedule;

import com.example.super_doc_clone_be.schedule.dtos.CreateScheduleDTO;
import com.example.super_doc_clone_be.schedule.dtos.ScheduleDTO;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/doctors/{id}/schedule")
public class ScheduleController {
    private final ScheduleService scheduleService;

    ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @GetMapping()
    public ScheduleDTO findByDateAndDoctor(@PathVariable Integer id, @RequestParam LocalDate date) {
        return this.scheduleService.findByDateAndDoctor(date, id);
    }

    @PostMapping("/create")
    public boolean create(@PathVariable Integer id, @RequestBody CreateScheduleDTO createScheduleDTO) {
        return scheduleService.create(id, createScheduleDTO);
    }
}
