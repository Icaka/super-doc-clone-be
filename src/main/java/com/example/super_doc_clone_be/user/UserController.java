package com.example.super_doc_clone_be.user;

import com.example.super_doc_clone_be.user.dtos.CreateUserDTO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public User findById(@PathVariable Integer id) {
        return userService.findById(id);
    }

    @GetMapping
    public List<User> findAll() {
        return userService.findAll();
    }

    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable Integer id) {
        return userService.delete(id);
    }

    @PostMapping("/create")
    public boolean create(@RequestBody CreateUserDTO createUserDTO) {
        return userService.create(createUserDTO);
    }

    @PutMapping("/{id}")
    public boolean update(@PathVariable Integer id, @RequestBody CreateUserDTO createUserDTO) {
        return userService.update(id, createUserDTO);
    }
}
