package com.example.super_doc_clone_be.user;

import com.example.super_doc_clone_be.security.CurrentUserService;
import com.example.super_doc_clone_be.user.dtos.CreateUserDTO;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final CurrentUserService currentUserService;

    UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, CurrentUserService currentUserService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.currentUserService = currentUserService;
    }

    public User findById(Integer id) {
        return userRepository.findById(id).orElseThrow();
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public boolean delete(Integer id) {
        if (!Objects.equals(currentUserService.getRole(), "ROLE_ADMIN")) {
            throw new RuntimeException("No Admin rights");
        }
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public boolean create(CreateUserDTO createUserDTO) {
        if (userRepository.findByEmail(createUserDTO.email()).isPresent()) {
            throw new RuntimeException("User already exists");
        }
        User temp = new User();
        temp.setEmail(createUserDTO.email());
        temp.setPassword(passwordEncoder.encode(createUserDTO.password()));
        temp.setRole("ROLE_USER");
        temp.setFirstName(createUserDTO.firstName());
        temp.setLastName(createUserDTO.lastName());
        temp.setDateOfBirth(createUserDTO.dateOfBirth());
        userRepository.save(temp);
        return true;
    }

    public boolean update(Integer id, CreateUserDTO createUserDTO) {
        User temp = userRepository.findById(id).orElseThrow();
        temp.setFirstName(createUserDTO.firstName());
        temp.setLastName(createUserDTO.lastName());
        temp.setDateOfBirth(createUserDTO.dateOfBirth());
        userRepository.save(temp);
        return true;
    }
}
