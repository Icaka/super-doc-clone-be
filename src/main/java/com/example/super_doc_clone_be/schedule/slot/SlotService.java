package com.example.super_doc_clone_be.schedule.slot;

import com.example.super_doc_clone_be.appointment.Appointment;
import com.example.super_doc_clone_be.appointment.AppointmentRepository;
import com.example.super_doc_clone_be.appointment.AppointmentStatus;
import com.example.super_doc_clone_be.schedule.Schedule;
import com.example.super_doc_clone_be.schedule.ScheduleRepository;
import com.example.super_doc_clone_be.schedule.slot.dtos.ChangeEndTimeDTO;
import com.example.super_doc_clone_be.security.CurrentUserService;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

@Service
public class SlotService {
    private final ScheduleRepository scheduleRepository;
    private final SlotRepository slotRepository;
    private final CurrentUserService currentUserService;
    private final AppointmentRepository appointmentRepository;

    SlotService(ScheduleRepository scheduleRepository, SlotRepository slotRepository, CurrentUserService currentUserService, AppointmentRepository appointmentRepository) {
        this.scheduleRepository = scheduleRepository;
        this.slotRepository = slotRepository;
        this.currentUserService = currentUserService;
        this.appointmentRepository = appointmentRepository;
    }

    public void defaultSlotCreation(Integer scheduleId, LocalTime workStart, Integer slotLength, Integer slotCount) {
        Schedule tempSchedule = this.scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new RuntimeException("No such schedule in Schedule Repository"));
        for (int i = 0; i < slotCount; i++) {
            Slot temp = new Slot();
            temp.setStartTime(workStart.plusMinutes(slotLength * i));
            temp.setEndTime(workStart.plusMinutes(slotLength * i).plusMinutes(slotLength));
            temp.setNumber(i + 1);
            temp.setStatus(SlotStatus.AVAILABLE);
            temp.setSchedule(tempSchedule);
            this.slotRepository.save(temp);
            if (temp.getEndTime().isAfter(tempSchedule.getWorkEnd())) {
                break;
            }
        }
    }

    public Boolean blockSlot(Integer doctor_id, Integer slot_id) {
        if (!Objects.equals(doctor_id, currentUserService.getId())) {
            throw new RuntimeException("Not slot owner");
        }
        Slot temp = slotRepository.findById(slot_id)
                .orElseThrow(() -> new RuntimeException("Slot with this id doesn't exist in db"));
        temp.setStatus(SlotStatus.BLOCKED);
        cancelAppointment(temp);
        slotRepository.save(temp);
        return true;
    }

    public void cancelAppointment(Slot slot) {
        //TODO maybe move logic to AppointmentService
        List<Appointment> temp = this.appointmentRepository.findBySlot(slot);
        if (temp.isEmpty()) {
            return;
        }
        Appointment tempAppointment = temp.getFirst();
        tempAppointment.setSlot(null);
        tempAppointment.setStatus(AppointmentStatus.CANCELLED);
        this.appointmentRepository.save(tempAppointment);
    }

    public boolean changeSlotEndTime(Integer slotId, ChangeEndTimeDTO change) {
        Slot slot = this.slotRepository.findById(slotId)
                .orElseThrow(() -> new RuntimeException("No slot with such id in db"));
        if (change.endTime().isBefore(slot.getStartTime())) {
            throw new RuntimeException("Can't set end time before start time");
        }
        if (change.endTime().isAfter(slot.getSchedule().getWorkEnd())) {
            throw new RuntimeException("End time is beyond the scope of the workday");
        }
        //TODO handling unfound
        Slot nextSlot = this.slotRepository.findByScheduleAndNumber(slot.getSchedule(), slot.getNumber() + 1).getFirst();
        if (change.endTime().isAfter(nextSlot.getEndTime())) {
            throw new RuntimeException("Can't go beyond next slot");
        }
        slot.setEndTime(change.endTime());
        nextSlot.setStartTime(change.endTime());
        this.slotRepository.save(slot);
        this.slotRepository.save(nextSlot);
        return true;
    }

    public boolean mergeAdjacentSlots(Integer slotId1, Integer slotId2) {
        Slot slot1 = this.slotRepository.findById(slotId1)
                .orElseThrow(() -> new RuntimeException("No slot with such id in db"));
        Slot slot2 = this.slotRepository.findById(slotId2)
                .orElseThrow(() -> new RuntimeException("No slot with such id in db"));
        if (slot1.getNumber() != slot2.getNumber() - 1) {
            throw new RuntimeException("Slots aren't adjacent");
        }
        slot1.setEndTime(slot2.getEndTime());
        this.slotRepository.deleteById(slotId2);
        //TODO handling unfound
        List<Slot> slotsAfter = this.slotRepository.findByScheduleAndNumberGreaterThan(slot1.getSchedule(), slot1.getNumber());
        for (Slot slot : slotsAfter) {
            slot.setNumber(slot.getNumber() - 1);
            this.slotRepository.save(slot);
        }
        return true;
    }
}
