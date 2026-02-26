package com.example.super_doc_clone_be.schedule.slot;

import com.example.super_doc_clone_be.schedule.ScheduleRepository;
import org.springframework.stereotype.Service;

import java.time.LocalTime;

@Service
public class SlotService {
    private final ScheduleRepository scheduleRepository;
    private final SlotRepository slotRepository;

    SlotService(ScheduleRepository scheduleRepository, SlotRepository slotRepository) {
        this.scheduleRepository = scheduleRepository;
        this.slotRepository = slotRepository;
    }

    boolean defaultPopulate(Integer scheduleId, LocalTime workStart, Integer slotLength, Integer slotCount) {
        Slot temp = new Slot();
        LocalTime tempTime = workStart;
        for (int i = 0; i < slotCount; i++) {
            tempTime = workStart.plusMinutes(slotLength * i);
            temp.setStartTime(tempTime);
            tempTime = tempTime.plusMinutes(slotLength);
            temp.setEndTime(tempTime);
            temp.setStatus(SlotStatus.AVAILABLE);
            temp.setSchedule(this.scheduleRepository.findById(scheduleId)
                    .orElseThrow(() -> new RuntimeException("No such schedule in Schedule Repository")));
            this.slotRepository.save(temp);
        }
        return true;
    }
}
