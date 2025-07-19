package com.example.super_doc_clone_be.review;

import com.example.super_doc_clone_be.review.dtos.CreateReviewDTO;
import com.example.super_doc_clone_be.review.dtos.ReviewDTO;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/create")
    public boolean create(@PathVariable Integer id, @RequestBody CreateReviewDTO createReviewDTO) {
        return reviewService.create(id, createReviewDTO);
    }
}
