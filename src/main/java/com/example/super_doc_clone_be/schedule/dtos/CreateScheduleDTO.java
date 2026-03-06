package com.example.super_doc_clone_be.schedule.dtos;

import java.time.LocalDate;
import java.time.LocalTime;

public record CreateScheduleDTO(
        Integer count,
        Integer length,
        LocalTime workStart,
        LocalTime workEnd,
        LocalDate date
) {
}
