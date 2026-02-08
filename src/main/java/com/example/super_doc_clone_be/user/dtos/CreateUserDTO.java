package com.example.super_doc_clone_be.user.dtos;

import java.util.Date;

public record CreateUserDTO(
        String email,
        String password,
        String firstName,
        String lastName,
        Date dateOfBirth
) {
}
