package com.example.super_doc_clone_be.appointment;

import com.example.super_doc_clone_be.appointment.dtos.AppointmentDTO;
import com.example.super_doc_clone_be.appointment.dtos.CreateAppointmentDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Boolean> create(@PathVariable Integer id, @RequestBody CreateAppointmentDTO createAppointmentDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(this.appointmentService.create(id, createAppointmentDTO));
    }

    @GetMapping("/{user_id}")
    List<AppointmentDTO> findByDoctorAndUserId(@PathVariable Integer id, @PathVariable Integer user_id) {
        return this.appointmentService.findByDoctorAndUserId(id, user_id);
    }

    @PutMapping("/cancel")
    public ResponseEntity<Boolean> cancel(@PathVariable Integer id, @RequestBody CreateAppointmentDTO createAppointmentDTO) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(this.appointmentService.cancel(id, createAppointmentDTO));
    }
}
