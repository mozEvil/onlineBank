package ru.mozevil.bank.entities;


import lombok.Data;

import java.util.List;

@Data
public class User {
    private Long id;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;

    private boolean enabled;

    private PrimaryAccount primaryAccount;
    private SavingAccount savingAccount;
    private List<Appointment> appointments;
    private List<Recipient> recipients;
}
