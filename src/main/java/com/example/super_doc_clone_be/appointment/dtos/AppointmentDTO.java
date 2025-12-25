package com.example.super_doc_clone_be.appointment.dtos;

import java.time.LocalDate;

public record AppointmentDTO(
        Integer id,
        LocalDate date,
        Integer slot,
        Integer userId
) {
}
