package com.example.backend.appointment;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {
    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @PostMapping
    public Appointment book(@RequestBody Appointment appointment) {
        return appointmentService.bookAppointment(appointment);
    }

    @GetMapping("/patient/{id}")
    public List<Appointment> getByPatient(@PathVariable Integer id) {
        return appointmentService.getByPatient(id);
    }

    @GetMapping("/doctor/{id}")
    public List<Appointment> getByDoctor(@PathVariable Integer id) {
        return appointmentService.getByDoctor(id);
    }

    @DeleteMapping("/{id}")
    public void cancel(@PathVariable Integer id) {
        appointmentService.cancelAppointment(id);
    }
}
