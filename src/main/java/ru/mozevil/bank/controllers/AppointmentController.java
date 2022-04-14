package ru.mozevil.bank.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.mozevil.bank.entities.Appointment;
import ru.mozevil.bank.entities.User;
import ru.mozevil.bank.services.AppointmentService;
import ru.mozevil.bank.services.UserService;

import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@RequestMapping("/appointment")
@RequiredArgsConstructor
public class AppointmentController {
    private final UserService userService;
    private final AppointmentService appointmentService;

    @GetMapping("/create")
    public String createAppointment(Model model) {
        Appointment appointment = new Appointment();
        model.addAttribute("appointment", appointment);
        model.addAttribute("dateString", "");
        return "appointment";
    }

    @PostMapping("/create")
    public String createAppointmentPost(@ModelAttribute("appointment") Appointment appointment,
                                        @ModelAttribute("dateString") String date,
                                        Principal principal) throws ParseException {
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        Date d1 = format1.parse(date);
        appointment.setDate(d1);
        User user = userService.findByUsername(principal.getName());
        appointment.setUser(user);
        appointmentService.createAppointment(appointment);
        return "redirect:/userFront";
    }

}
