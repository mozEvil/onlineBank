package ru.mozevil.bank.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mozevil.bank.entities.Account;
import ru.mozevil.bank.entities.AccountType;
import ru.mozevil.bank.entities.Transaction;
import ru.mozevil.bank.entities.User;
import ru.mozevil.bank.repositories.AccountRepository;
import ru.mozevil.bank.repositories.TransactionRepository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    public Account createAccount(User user, AccountType type) {
        Account account = new Account();
        account.setUser(user);
        account.setType(type);
        account.setAccountBalance(new BigDecimal("0.0"));
        account.setAccountNumber(generateAccNum());
        return account;
    }

    private int generateAccNum() {
        Random rnd = new Random();
        int num = 2022 * 10 + rnd.nextInt(10);
        num = num * 10 + rnd.nextInt(10);
        num = num * 10 + rnd.nextInt(10);
        num = num * 10 + rnd.nextInt(10);
        num = num * 10 + rnd.nextInt(10);
        num = num * 10 + rnd.nextInt(10);
        return num;
    }

    public Account getAccount(String username, AccountType type) {
        return accountRepository.getAccountByUsername(username, type);
    }

    @Transactional
    public void deposit(String username, double amount, AccountType type) {
        Account account = getAccount(username, type);
        account.setAccountBalance(account.getAccountBalance().add(new BigDecimal(amount)));
        accountRepository.save(account);

        Date date = new Date();

        Transaction transaction = new Transaction();
        transaction.setAccount(account);
        transaction.setDescription("Deposit from " + type.name() + " Account");
        transaction.setType("Deposit");
        transaction.setStatus("Finished");
        transaction.setAmount(amount);
        transaction.setAvailableBalance(account.getAccountBalance());
        transaction.setDate(date);
        transactionRepository.save(transaction);
    }

    public void withdraw(String username, Double amount, AccountType type) {
        Account account = getAccount(username, type);
        account.setAccountBalance(account.getAccountBalance().subtract(new BigDecimal(amount)));
        accountRepository.save(account);

        Date date = new Date();

        Transaction transaction = new Transaction();
        transaction.setAccount(account);
        transaction.setDescription("Withdraw from " + type.name() + " Account");
        transaction.setType("Withdraw");
        transaction.setStatus("Finished");
        transaction.setAmount(amount);
        transaction.setAvailableBalance(account.getAccountBalance());
        transaction.setDate(date);
        transactionRepository.save(transaction);
    }
}
