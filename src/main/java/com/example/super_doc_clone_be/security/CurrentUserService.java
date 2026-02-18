package com.example.super_doc_clone_be.security;

import com.example.super_doc_clone_be.user.User;
import com.example.super_doc_clone_be.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CurrentUserService {

    private final UserRepository userRepository;

    public User get() {
        String email = SecurityUtils.getCurrentUsername();
        return userRepository.findByEmail(email).orElseThrow();
    }

    public Integer getId() {
        return get().getId();
    }

    public String getRole() {
        return get().getRole();
    }
}

