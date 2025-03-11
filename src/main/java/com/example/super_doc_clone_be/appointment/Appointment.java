package com.example.super_doc_clone_be.appointment;

import com.example.super_doc_clone_be.doctors.Doctor;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.util.Date;

@Table(name = "appointment")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;

    @Column(name = "date")
    private Date date;

    @Column(name = "slot")
    private Integer slot;

    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor_id;
}
