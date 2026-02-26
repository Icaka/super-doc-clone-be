package com.example.super_doc_clone_be.schedule.slot;

import com.example.super_doc_clone_be.schedule.ScheduleRepository;

public class SlotService {
    private final ScheduleRepository scheduleRepository;

    SlotService(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }
}
