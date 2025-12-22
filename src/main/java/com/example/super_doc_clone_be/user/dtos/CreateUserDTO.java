package com.example.super_doc_clone_be.user.dtos;

import java.util.Date;

public record CreateUserDTO(
        String firstName,
        String lastName,
        Date dateOfBirth
) {
}
