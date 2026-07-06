package com.example.backend.patient;


import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class PatientService {

    private final PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public Patient createPatient(Patient patient) {
        if(patientRepository.existsByEmail(patient.getEmail())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already exists");
        }

        this.patientRepository.save(patient);
        return patient;
    }

    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }


    public Patient getPatientById(Integer id) {
        return patientRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Patient not found"));
    }

    public Patient updatePatient(Integer id, Patient updatedpatient) {
        Patient patient = getPatientById(id);

        patient.setFirstName(updatedpatient.getFirstName());
        patient.setLastName(updatedpatient.getLastName());
        patient.setEmail(updatedpatient.getEmail());
        patient.setPhoneNumber(updatedpatient.getPhoneNumber());

        patientRepository.save(patient);
        return patient;
    }

    public void deletePatient(Integer id) {
        patientRepository.deleteById(id);
    }
}
