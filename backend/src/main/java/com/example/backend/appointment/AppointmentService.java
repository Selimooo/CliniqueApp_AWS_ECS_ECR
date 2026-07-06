package com.example.backend.appointment;

import com.example.backend.timeslot.TimeSlot;
import com.example.backend.timeslot.TimeSlotRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final TimeSlotRepository timeSlotRepository;

    public AppointmentService(AppointmentRepository appointmentRepository, TimeSlotRepository timeSlotRepository) {
        this.appointmentRepository = appointmentRepository;
        this.timeSlotRepository = timeSlotRepository;
    }


    public Appointment bookAppointment(Appointment appointment) {
        Integer slotId = appointment.getTimeslot().getId();

        TimeSlot timeSlot = timeSlotRepository.findById(slotId).orElseThrow(
                () -> new RuntimeException("TimeSlot not found")
        );

        if (!timeSlot.getAvailable()) {
            throw new RuntimeException("TimeSlot not available");
        }

        if (appointmentRepository.existsByTimeslotId(slotId)) {
            throw new RuntimeException("Appointment already exists for this slot");
        }

        appointment.setDoctor(timeSlot.getDoctor());
        timeSlot.setAvailable(false);
        timeSlotRepository.save(timeSlot);

        appointment.setStatus(AppointmentStatus.BOOKED);
        appointmentRepository.save(appointment);
        return appointment;
    }

    public List<Appointment> getByPatient(Integer id) {
        return appointmentRepository.findByPatientId(id);
    }

    public List<Appointment> getByDoctor(Integer id) {
        return appointmentRepository.findByDoctorId(id);
    }

    public void cancelAppointment(Integer id) {
        Appointment appointment = appointmentRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Appointment not found")
        );

        TimeSlot timeSlot = appointment.getTimeslot();
        timeSlot.setAvailable(true);
        timeSlotRepository.save(timeSlot);

        appointment.setStatus(AppointmentStatus.CANCELLED);
        appointmentRepository.save(appointment);
    }
}