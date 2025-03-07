package com.example.super_doc_clone_be.doctors;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DoctorRepository extends JpaRepository<Doctor, Integer> {
    List<Doctor> findByFirstNameContaining(String query);
    List<Doctor> findByLastNameContaining(String query);
    List<Doctor> findByFirstNameContainingOrLastNameContaining(String fName, String lName);
    @Query("SELECT d FROM Doctor d WHERE d.firstName LIKE CONCAT('%',:query,'%')")
    List<Doctor> findByNameLike(String query);
}
