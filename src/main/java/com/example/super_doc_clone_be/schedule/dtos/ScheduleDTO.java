package com.example.super_doc_clone_be.schedule.dtos;

import java.sql.Time;
import java.time.LocalDate;

public record ScheduleDTO(
        Integer id,
        Integer count,
        Integer length,
        Time start,
        LocalDate date
) {
}
