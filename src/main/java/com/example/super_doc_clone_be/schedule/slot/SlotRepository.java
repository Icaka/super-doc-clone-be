package com.example.super_doc_clone_be.schedule.slot;

import com.example.super_doc_clone_be.schedule.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SlotRepository extends JpaRepository<Slot, Integer> {
    List<Slot> findBySchedule_IdAndNumber(Integer slotId, Integer lotNumber);

    List<Slot> findByScheduleAndNumberGreaterThan(Schedule schedule, Integer numberIsGreaterThan);

    List<Slot> findByScheduleAndNumber(Schedule schedule, Integer number);
}
