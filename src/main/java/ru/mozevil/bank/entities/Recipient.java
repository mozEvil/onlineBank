package ru.mozevil.bank.entities;

import lombok.Data;

@Data
public class Recipient {
    private Long id;
    private String name;
    private String email;
    private String phone;
    private String accountNumber;
    private String description;

    private User user;
}
