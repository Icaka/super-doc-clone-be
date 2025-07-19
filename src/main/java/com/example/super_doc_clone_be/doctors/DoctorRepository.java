package com.example.super_doc_clone_be.doctors;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DoctorRepository extends JpaRepository<Doctor, Integer> {

    @Query("SELECT d FROM Doctor d WHERE " +
            "(LOWER(CONCAT(d.firstName, ' ', d.lastName)) LIKE LOWER(CONCAT('%',:query,'%')))")
    List<Doctor> findByNameLike(String query);
}
