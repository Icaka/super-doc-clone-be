package com.example.super_doc_clone_be.review;

import com.example.super_doc_clone_be.review.dtos.ReviewDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;

    ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public List<ReviewDTO> findByDoctorId(final Integer id) {

        return this.reviewRepository.findByDoctorId(id).stream()
                .map(r -> new ReviewDTO(r.getId(), r.getScore(), r.getText())).toList();
    }
}
