package com.example.backend.doctor;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorService {
    private final DoctorRepository doctorRepository;

    public DoctorService(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }


    public Doctor createDoctor(Doctor doctor) {
        if(doctorRepository.existsByEmail(doctor.getEmail())){
            throw new RuntimeException("Email already exists");
        }
        return doctorRepository.save(doctor);

    }

    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }

    public Doctor getDoctorById(Integer id) {
        return doctorRepository.findById(id).orElseThrow( () -> new RuntimeException("Doctor not found"));
    }

    public Doctor updateDoctor(Integer id, Doctor updateddoctor) {
        Doctor doctor = getDoctorById(id);
        doctor.setFirstName(updateddoctor.getFirstName());
        doctor.setLastName(updateddoctor.getLastName());
        doctor.setEmail(updateddoctor.getEmail());
        doctor.setSpecialty(updateddoctor.getSpecialty());
        doctor.setPhoneNumber(updateddoctor.getPhoneNumber());
        return doctorRepository.save(doctor);
    }

    public List<Doctor> getBySpecialty(String specialty) {
        return doctorRepository.findAllBySpecialty(specialty);
    }

    public void deleteDoctor(Integer id) {
        doctorRepository.deleteById(id);
    }
}
