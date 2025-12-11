package com.example.super_doc_clone_be.appointment.dtos;

import java.time.LocalDate;

public record CreateAppointmentDTO(
        LocalDate date,
        Integer slot
){}
