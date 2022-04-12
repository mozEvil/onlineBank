package ru.mozevil.bank.entities;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class PrimaryAccount {
    private Long id;
    private int accountNumber;
    private BigDecimal accountBalance;

    private List<PrimaryTransaction> primaryTransactions;
}
