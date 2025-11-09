package com.example.super_doc_clone_be.doctors;

import com.example.super_doc_clone_be.doctors.dtos.CreateDoctorDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorService {
    private final DoctorRepository doctorRepository;

    DoctorService(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    public Doctor findById(Integer id){
        return doctorRepository.findById(id).orElseThrow();
    }

    public List<Doctor> findAll(){
        return doctorRepository.findAll();
    }

    public boolean delete(Integer id){
        if (doctorRepository.existsById(id)){
            doctorRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public boolean create(CreateDoctorDTO createDoctorDTO){
        Doctor temp = new Doctor();
        temp.setFirstName(createDoctorDTO.firstName());
        temp.setLastName(createDoctorDTO.lastName());
        temp.setPicture(createDoctorDTO.picture());
        temp.setDateOfBirth(createDoctorDTO.dateOfBirth());
        temp.setDescription(createDoctorDTO.description());
        temp.setRating(createDoctorDTO.rating());
        doctorRepository.save(temp);
        return true;
    }

    public boolean update(Integer id, CreateDoctorDTO createDoctorDTO) {
        Doctor temp = doctorRepository.findById(id).orElseThrow();
        temp.setFirstName(createDoctorDTO.firstName());
        temp.setLastName(createDoctorDTO.lastName());
        temp.setPicture(createDoctorDTO.picture());
        temp.setDateOfBirth(createDoctorDTO.dateOfBirth());
        temp.setDescription(createDoctorDTO.description());
        temp.setRating(createDoctorDTO.rating());
        doctorRepository.save(temp);
        return true;
    }

    public List<Doctor> searchByQuery(String query){
        return doctorRepository.findByNameLike(query);
    }
}
