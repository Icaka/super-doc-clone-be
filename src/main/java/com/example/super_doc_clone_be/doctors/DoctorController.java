package com.example.super_doc_clone_be.doctors;

import com.example.super_doc_clone_be.doctors.dtos.CreateDoctorDTO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/doctors")
public class DoctorController {
    private final DoctorService doctorService;
    DoctorController(DoctorService doctorService){
        this.doctorService = doctorService;
    }
    @GetMapping("/{id}")
    public Doctor findById(@PathVariable Integer id){
        return doctorService.findById(id);
    }

    @GetMapping
    public List<Doctor> findAll(){
        return doctorService.findAll();
    }

    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable Integer id){
        return doctorService.delete(id);
    }

    @PostMapping()
    public boolean create(@RequestBody CreateDoctorDTO createDoctorDTO){
        return doctorService.create(createDoctorDTO);
    }

    @PutMapping("/{id}")
    public boolean update(@PathVariable Integer id,@RequestBody CreateDoctorDTO createDoctorDTO){
        return doctorService.update(id, createDoctorDTO);
    }

    @GetMapping("/search/{query}")
    public List<Doctor> search(@PathVariable String query){
        return doctorService.searchByQuery(query);
    }
}
