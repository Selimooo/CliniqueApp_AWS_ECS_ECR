package com.example.backend.timeslot;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/timeslots")
public class TimeSlotController {

    private final TimeSlotService timeSlotService;

    public TimeSlotController(TimeSlotService timeSlotService) {
        this.timeSlotService = timeSlotService;
    }

    @PostMapping
    public TimeSlot create(@RequestBody TimeSlot slot) {
        return timeSlotService.createSlot(slot);
    }

    @GetMapping
    public List<TimeSlot> getAll() {
        return timeSlotService.getAll();
    }

    @GetMapping("/doctor/{id}")
    public List<TimeSlot> getByDoctor(@PathVariable Integer id) {
        return timeSlotService.getByDoctor(id);
    }

    @GetMapping("/doctor/{id}/available")
    public List<TimeSlot> getAvailable(@PathVariable Integer id) {
        return timeSlotService.getAvailableByDoctor(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        timeSlotService.delete(id);
    }
}
