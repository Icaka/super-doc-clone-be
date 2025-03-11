package com.example.super_doc_clone_be.appointment.dtos;

import java.util.Date;

public record CreateAppointmentDTO(
    Date date,
    Integer slot
){}
