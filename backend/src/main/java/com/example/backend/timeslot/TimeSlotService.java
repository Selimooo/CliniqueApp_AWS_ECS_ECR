package com.example.backend.timeslot;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TimeSlotService {

    private final TimeSlotRepository timeSlotRepository;

    public TimeSlotService(TimeSlotRepository timeSlotRepository) {
        this.timeSlotRepository = timeSlotRepository;
    }

    public TimeSlot createSlot(TimeSlot slot) {
        return timeSlotRepository.save(slot);
    }

    public List<TimeSlot> getAll() {
        return timeSlotRepository.findAll();
    }

    public List<TimeSlot> getByDoctor(Integer doctorId) {
        return timeSlotRepository.findByDoctorId(doctorId);
    }

    public List<TimeSlot> getAvailableByDoctor(Integer doctorId) {
        return timeSlotRepository.findByDoctorIdAndAvailableTrue(doctorId);
    }

    public void delete(Integer id) {
        timeSlotRepository.deleteById(id);
    }
}
