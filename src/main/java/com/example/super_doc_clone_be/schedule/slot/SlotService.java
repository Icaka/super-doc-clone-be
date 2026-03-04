package com.example.super_doc_clone_be.schedule.slot;

import com.example.super_doc_clone_be.schedule.ScheduleRepository;
import com.example.super_doc_clone_be.security.CurrentUserService;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.Objects;

@Service
public class SlotService {
    private final ScheduleRepository scheduleRepository;
    private final SlotRepository slotRepository;
    private final CurrentUserService currentUserService;

    SlotService(ScheduleRepository scheduleRepository, SlotRepository slotRepository, CurrentUserService currentUserService) {
        this.scheduleRepository = scheduleRepository;
        this.slotRepository = slotRepository;
        this.currentUserService = currentUserService;
    }

    public void defaultSlotCreation(Integer scheduleId, LocalTime workStart, Integer slotLength, Integer slotCount) {
        for (int i = 0; i < slotCount; i++) {
            Slot temp = new Slot();
            temp.setStartTime(workStart.plusMinutes(slotLength * i));
            temp.setEndTime(workStart.plusMinutes(slotLength * i).plusMinutes(slotLength));
            temp.setNumber(i + 1);
            temp.setStatus(SlotStatus.AVAILABLE);
            temp.setSchedule(this.scheduleRepository.findById(scheduleId)
                    .orElseThrow(() -> new RuntimeException("No such schedule in Schedule Repository")));
            this.slotRepository.save(temp);
        }
    }

    public Boolean blockSlot(Integer doctor_id, Integer slot_id) {
        if (!Objects.equals(doctor_id, currentUserService.getId())) {
            throw new RuntimeException("Not slot owner");
        }
        Slot temp = slotRepository.findById(slot_id)
                .orElseThrow(() -> new RuntimeException("Slot with this id doesn't exist in db"));
        temp.setStatus(SlotStatus.BLOCKED); //TODO what happens if slot is booked
        slotRepository.save(temp);
        return true;
    }
}
