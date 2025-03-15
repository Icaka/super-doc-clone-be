package com.example.super_doc_clone_be.appointment.dtos;

import java.time.LocalDate;
import java.util.Date;

public record CreateAppointmentDTO(
        LocalDate date,
        Integer slot
){}
