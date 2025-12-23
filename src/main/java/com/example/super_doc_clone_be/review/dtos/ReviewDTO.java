package com.example.super_doc_clone_be.review.dtos;

public record ReviewDTO(
        Integer id,
        Integer score,
        String text,
        Integer user_id
) {
}
