package ru.mozevil.bank.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mozevil.bank.entities.Appointment;
import ru.mozevil.bank.repositories.AppointmentRepository;

@Service
@RequiredArgsConstructor
public class AppointmentService {
    private final AppointmentRepository appointmentRepository;

    public void createAppointment(Appointment appointment) {
        appointmentRepository.save(appointment);
    }
}
