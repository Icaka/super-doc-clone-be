package com.example.super_doc_clone_be.user;

import com.example.super_doc_clone_be.user.dtos.CreateUserDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findById(Integer id) {
        return userRepository.findById(id).orElseThrow();
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public boolean delete(Integer id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public boolean create(CreateUserDTO createUserDTO) {
        User temp = new User();
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
