package com.example.super_doc_clone_be.schedule.dtos;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public record ScheduleDTO(
        Integer id,
        Integer count,
        Integer length,
        LocalTime workStart,
        LocalTime workEnd,
        LocalDate date,
        List<Integer> bookedSlots
) {
}
