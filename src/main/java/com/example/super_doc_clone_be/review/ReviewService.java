package com.example.super_doc_clone_be.review;

import com.example.super_doc_clone_be.doctors.DoctorRepository;
import com.example.super_doc_clone_be.review.dtos.CreateReviewDTO;
import com.example.super_doc_clone_be.review.dtos.ReviewDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final DoctorRepository doctorRepository;

    ReviewService(ReviewRepository reviewRepository, DoctorRepository doctorRepository) {
        this.reviewRepository = reviewRepository;
        this.doctorRepository = doctorRepository;
    }

    public List<ReviewDTO> findByDoctorId(final Integer id) {

        return this.reviewRepository.findByDoctorId(id).stream()
                .map(r -> new ReviewDTO(r.getId(), r.getScore(), r.getText())).toList();
    }

    boolean create(Integer doctorId, CreateReviewDTO createReviewDTO) {
        Review temp = new Review();
        temp.setDoctor(doctorRepository.findById(doctorId).orElseThrow());
        temp.setScore(createReviewDTO.score());
        temp.setText(createReviewDTO.text());
        this.reviewRepository.save(temp);
        return true;
    }
}
