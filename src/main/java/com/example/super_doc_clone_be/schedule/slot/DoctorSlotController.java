package com.example.super_doc_clone_be.schedule.slot;

import com.example.super_doc_clone_be.schedule.slot.dtos.ChangeEndTimeDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping("/end")
    public ResponseEntity<Boolean> changeEndOfSlot(@PathVariable Integer doctor_id, @PathVariable Integer slot_id, @RequestBody ChangeEndTimeDTO change) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(this.slotService.changeSlotEndTime(slot_id, change));
    }

    @PutMapping("/merge")
    public ResponseEntity<Boolean> mergeAdjacentSlots(@PathVariable Integer doctor_id, @PathVariable Integer slot_id, @RequestBody Integer slotId, @RequestBody Integer slotId2) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(this.slotService.mergeAdjacentSlots(slotId, slotId2));
    }
}
