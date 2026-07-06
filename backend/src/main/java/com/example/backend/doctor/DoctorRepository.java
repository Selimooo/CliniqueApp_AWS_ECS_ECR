package com.example.backend.doctor;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DoctorRepository extends JpaRepository<Doctor,Integer> {
    boolean existsByEmail(String email);

    List<Doctor> findAllBySpecialty(String specialty);
}
