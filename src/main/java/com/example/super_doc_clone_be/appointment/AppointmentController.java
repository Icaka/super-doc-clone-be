package com.example.super_doc_clone_be.appointment;

import com.example.super_doc_clone_be.appointment.dtos.AppointmentDTO;
import com.example.super_doc_clone_be.appointment.dtos.CreateAppointmentDTO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/doctor/{id}/appointments")
public class AppointmentController {
    private final AppointmentService appointmentService;

    AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @GetMapping()
    List<AppointmentDTO> findByDoctorId(@PathVariable Integer id) {
        return this.appointmentService.findByDoctorId(id);
    }

    @PostMapping("/create")
    public boolean create(@PathVariable Integer id, @RequestBody CreateAppointmentDTO createAppointmentDTO){
        return this.appointmentService.create(id, createAppointmentDTO);
    }

}
