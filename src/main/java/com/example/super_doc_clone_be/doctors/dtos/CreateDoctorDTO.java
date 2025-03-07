package com.example.super_doc_clone_be.doctors.dtos;

import java.util.Date;

public record CreateDoctorDTO(
        String firstName,
        String lastName,
        String picture,
        Float rating,
        Date dateOfBirth,
        String description
) {

}
