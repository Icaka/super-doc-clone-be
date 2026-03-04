package com.example.super_doc_clone_be.schedule.slot;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/doctor/{doctor_id}/slot/{slot_id}")
public class DoctorSlotController {
    private final SlotService slotService;

    DoctorSlotController(SlotService slotService) {
        this.slotService = slotService;
    }

    @PutMapping("/block")
    public ResponseEntity<Boolean> blockSlot(@PathVariable Integer doctor_id, @PathVariable Integer slot_id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(this.slotService.blockSlot(doctor_id, slot_id));
    }
}
