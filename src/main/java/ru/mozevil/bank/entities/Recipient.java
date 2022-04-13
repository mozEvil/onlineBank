package ru.mozevil.bank.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Recipient {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String email;
    private String phone;
    private String accountNumber;
    private String description;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;
}
