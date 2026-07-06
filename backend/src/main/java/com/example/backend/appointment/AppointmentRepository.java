package com.example.backend.appointment;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment,Integer> {
    boolean existsByTimeslotId(Integer slotId);

    List<Appointment> findByPatientId(Integer patientId);

    List<Appointment> findByDoctorId(Integer id);
}
