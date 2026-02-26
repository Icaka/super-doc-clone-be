package com.example.super_doc_clone_be.schedule;

import com.example.super_doc_clone_be.appointment.Appointment;
import com.example.super_doc_clone_be.appointment.AppointmentRepository;
import com.example.super_doc_clone_be.doctors.DoctorRepository;
import com.example.super_doc_clone_be.schedule.dtos.CreateScheduleDTO;
import com.example.super_doc_clone_be.schedule.dtos.ScheduleDTO;
import com.example.super_doc_clone_be.schedule.slot.SlotService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final DoctorRepository doctorRepository;
    private final AppointmentRepository appointmentRepository;
    private final SlotService slotService;

    ScheduleService(ScheduleRepository scheduleRepository, DoctorRepository doctorRepository, AppointmentRepository appointmentRepository, SlotService slotService) {
        this.doctorRepository = doctorRepository;
        this.scheduleRepository = scheduleRepository;
        this.appointmentRepository = appointmentRepository;
        this.slotService = slotService;
    }

    public ScheduleDTO findByDateAndDoctor(LocalDate date, Integer doctorId) {
        Schedule temp = scheduleRepository.findByDateAndDoctor_Id(date, doctorId);
        if (temp == null) {
            return new ScheduleDTO(0, 0, 0, null, null, null, null);
        }

        List<Appointment> tempAppointments = appointmentRepository.findByDoctorIdAndDate(doctorId, date);
        List<Integer> takenSlots = new ArrayList<Integer>();
        for (Appointment a : tempAppointments) {
            takenSlots.add(a.getSlot());
        }
        return new ScheduleDTO(temp.getId(), temp.getSlotCount(), temp.getSlotLength(), temp.getWorkStart(), temp.getWorkEnd(), temp.getDate(), takenSlots);
    }

    boolean create(Integer doctorId, CreateScheduleDTO createReviewDTO) {
        Schedule temp = new Schedule();
        temp.setDoctor(doctorRepository.findById(doctorId).orElseThrow());
        temp.setSlotCount(createReviewDTO.count());
        temp.setSlotLength(createReviewDTO.length());
        temp.setWorkStart(createReviewDTO.workStart());
        temp.setWorkEnd(createReviewDTO.workEnd());
        temp.setDate(createReviewDTO.date());
        this.scheduleRepository.save(temp);
        return true;
    }
}