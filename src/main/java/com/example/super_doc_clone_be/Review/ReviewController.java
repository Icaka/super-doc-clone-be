package com.example.super_doc_clone_be.Review;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/reviews")
public class ReviewController {
    private final ReviewService reviewService;

    ReviewController(ReviewService reviewService){
        this.reviewService = reviewService;
    }

    @GetMapping("/{id}")
    public Review findById(@PathVariable Integer id){
        return this.reviewService.findById(id);
    }

    @GetMapping("/doctor/{id}")
    public List<Review> findByDoctorId(@PathVariable Integer id){
        return this.reviewService.findByDoctorId(id);
    }

    @GetMapping()
    public List<Review> findAll(){
        return this.reviewService.findAll();
    }
}
