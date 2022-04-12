package ru.mozevil.bank.entities;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class SavingAccount {
    private Long id;
    private int accountNumber;
    private BigDecimal accountBalance;

    private List<SavingsTransaction> savingsTransactionList;
}
