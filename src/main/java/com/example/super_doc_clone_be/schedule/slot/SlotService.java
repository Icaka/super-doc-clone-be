package com.example.super_doc_clone_be.schedule.slot;

import com.example.super_doc_clone_be.appointment.Appointment;
import com.example.super_doc_clone_be.appointment.AppointmentRepository;
import com.example.super_doc_clone_be.appointment.AppointmentStatus;
import com.example.super_doc_clone_be.schedule.Schedule;
import com.example.super_doc_clone_be.schedule.ScheduleRepository;
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
        cancelAppointment(slot_id);
        slotRepository.save(temp);
        return true;
    }

    public void cancelAppointment(Integer slotId) {
        //TODO maybe move logic to AppointmentService
        List<Appointment> temp = this.appointmentRepository.findBySlot_Id(slotId);
        if (temp.isEmpty()) {
            return;
        }
        Appointment tempAppointment = temp.getFirst();
        tempAppointment.setSlot(null);
        tempAppointment.setStatus(AppointmentStatus.CANCELLED);
        this.appointmentRepository.save(tempAppointment);
    }


}
