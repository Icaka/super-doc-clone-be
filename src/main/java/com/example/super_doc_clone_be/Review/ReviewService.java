package com.example.super_doc_clone_be.Review;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;

    ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public Review findById(final Integer id){
        return this.reviewRepository.findById(id).orElseThrow();
    }

    public List<Review> findByDoctorId(final Integer id) {
        return this.reviewRepository.findByDoctor_idEquals(id);
    }

    public List<Review> findAll(){
        return this.reviewRepository.findAll();
    }
}
