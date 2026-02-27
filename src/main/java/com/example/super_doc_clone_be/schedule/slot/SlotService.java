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

    public void defaultSlotCreation(Integer scheduleId, LocalTime workStart, Integer slotLength, Integer slotCount) {
        for (int i = 0; i < slotCount; i++) {
            Slot temp = new Slot();
            temp.setStartTime(workStart.plusMinutes(slotLength * i));
            temp.setEndTime(workStart.plusMinutes(slotLength * i).plusMinutes(slotLength));
            temp.setStatus(SlotStatus.AVAILABLE);
            temp.setSchedule(this.scheduleRepository.findById(scheduleId)
                    .orElseThrow(() -> new RuntimeException("No such schedule in Schedule Repository")));
            this.slotRepository.save(temp);
        }
    }
}
