package com.example.super_doc_clone_be.Review;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Integer> {
    @Query("SELECT r FROM Review r WHERE r.doctor_id.id = :id")
    List<Review> findByDoctor_idEquals(Integer id);
}
