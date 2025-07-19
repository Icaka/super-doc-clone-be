package com.example.super_doc_clone_be.review;

import com.example.super_doc_clone_be.review.dtos.ReviewDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/doctor/{id}/reviews")
public class ReviewController {
    private final ReviewService reviewService;

    ReviewController(ReviewService reviewService){
        this.reviewService = reviewService;
    }

    @GetMapping()
    public List<ReviewDTO> findByDoctorId(@PathVariable Integer id) {
        return this.reviewService.findByDoctorId(id);
    }
}
