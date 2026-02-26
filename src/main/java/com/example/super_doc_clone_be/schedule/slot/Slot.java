package com.example.super_doc_clone_be.schedule.slot;

import com.example.super_doc_clone_be.schedule.Schedule;
import jakarta.persistence.*;

import java.time.LocalTime;

@Entity
public class Slot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "startTime")
    private LocalTime startTime;

    @Column(name = "endTime")
    private LocalTime endTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private SlotStatus status;

    @ManyToOne
    @JoinColumn(name = "schedule_id", nullable = true)
    private Schedule schedule;
}
