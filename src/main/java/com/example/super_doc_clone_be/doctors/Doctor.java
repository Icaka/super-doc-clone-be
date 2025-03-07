package com.example.super_doc_clone_be.doctors;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Table(name = "doctors")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "picture")
    private String picture;

    @Column(name = "rating")
    private Float rating;

    @Column(name = "date_of_birth")
    private Date dateOfBirth;

    @Column(name = "description")
    private String description;


}
