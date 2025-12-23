package com.example.super_doc_clone_be.review;

import com.example.super_doc_clone_be.doctors.DoctorRepository;
import com.example.super_doc_clone_be.review.dtos.CreateReviewDTO;
import com.example.super_doc_clone_be.review.dtos.ReviewDTO;
import com.example.super_doc_clone_be.user.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final DoctorRepository doctorRepository;
    private final UserRepository userRepository;

    ReviewService(ReviewRepository reviewRepository, DoctorRepository doctorRepository, UserRepository userRepository) {
        this.reviewRepository = reviewRepository;
        this.doctorRepository = doctorRepository;
        this.userRepository = userRepository;
    }

    public List<ReviewDTO> findByDoctorId(final Integer id) {
        System.out.println("findByDoctorId");
        return this.reviewRepository.findByDoctorId(id).stream()
                .map(r -> new ReviewDTO(r.getId(), r.getScore(), r.getText(), r.getUser().getId())).toList();
    }

    public List<ReviewDTO> findByDoctorAndUserId(final Integer doctor_id, final Integer user_id) {
        return this.reviewRepository.findByDoctorIdAndUserId(doctor_id, user_id).stream()
                .map(r -> new ReviewDTO(r.getId(), r.getScore(), r.getText(), r.getUser().getId())).toList();
    }

    boolean create(Integer doctorId, CreateReviewDTO createReviewDTO) {
        Review temp = new Review();
        temp.setDoctor(doctorRepository.findById(doctorId).orElseThrow());
        temp.setScore(createReviewDTO.score());
        temp.setText(createReviewDTO.text());
        temp.setUser(userRepository.findById(createReviewDTO.user_id()).orElseThrow());
        this.reviewRepository.save(temp);
        return true;
    }
}
