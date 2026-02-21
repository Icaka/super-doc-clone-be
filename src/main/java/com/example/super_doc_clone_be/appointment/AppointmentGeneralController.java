package com.example.super_doc_clone_be.appointment;

import com.example.super_doc_clone_be.appointment.dtos.AppointmentDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/appointments")
public class AppointmentGeneralController {
    private final AppointmentService appointmentService;

    AppointmentGeneralController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @GetMapping("/me")
    List<AppointmentDTO> getMyAppointments() {
        return this.appointmentService.getLoggedUserAppointments();
    }

    @DeleteMapping("/cancel/{appointment_id}")
    public ResponseEntity<Boolean> cancelById(@PathVariable Integer appointment_id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(this.appointmentService.cancelById(appointment_id));
    }
}
