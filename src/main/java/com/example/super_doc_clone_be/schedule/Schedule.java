package com.example.super_doc_clone_be.schedule;

import com.example.super_doc_clone_be.doctors.Doctor;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.time.LocalDate;

@Table(name = "schedule")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "slotCount")
    private Integer slotCount;

    @Column(name = "slotLength")
    private Integer slotLength;

    @Column(name = "workStart")
    private Time workStart;

    @Column(name = "date")
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;
}
