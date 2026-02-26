package com.example.super_doc_clone_be.appointment;

import com.example.super_doc_clone_be.appointment.dtos.AppointmentDTO;
import com.example.super_doc_clone_be.security.CurrentUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/doctor/me")
public class DoctorAppointmentController {
    private final AppointmentService appointmentService;
    private final CurrentUserService currentUserService;

    DoctorAppointmentController(AppointmentService appointmentService, CurrentUserService currentUserService) {
        this.appointmentService = appointmentService;
        this.currentUserService = currentUserService;
    }

    @GetMapping
    public List<AppointmentDTO> getAppointments() {
        return this.appointmentService.findByDoctorId(currentUserService.getId());
    }

    @GetMapping("/appointment/{id}/status")
    public ResponseEntity<Boolean> setAppointmentStatus(@PathVariable Integer id, @RequestBody AppointmentStatus status) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(this.appointmentService.changeAppointmentStatus(id, status));
    }


}
