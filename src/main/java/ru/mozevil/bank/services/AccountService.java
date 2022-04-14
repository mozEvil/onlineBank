package ru.mozevil.bank.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mozevil.bank.entities.*;
import ru.mozevil.bank.repositories.AccountRepository;
import ru.mozevil.bank.repositories.RecipientRepository;
import ru.mozevil.bank.repositories.TransactionRepository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Random;

@Service @Slf4j
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final RecipientRepository recipientRepository;

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
    public void deposit(Account account, Double amount, String description) {
        account.setAccountBalance(account.getAccountBalance().add(new BigDecimal(amount)));
        accountRepository.save(account);

        Date date = new Date();

        Transaction transaction = new Transaction();
        transaction.setAccount(account);
        transaction.setDescription(description);
        transaction.setType("Deposit");
        transaction.setStatus("Finished");
        transaction.setAmount(amount);
        transaction.setAvailableBalance(account.getAccountBalance());
        transaction.setDate(date);
        transactionRepository.save(transaction);
    }

    public void deposit(Account account, Double amount) {
        String description = "Deposit to " + account.getType().name() + " Account";
        deposit(account, amount, description);
    }

    public void withdraw(Account account, Double amount) {
        String description = "Withdraw from " + account.getType().name() + " Account";
        withdraw(account, amount, description);
    }

    @Transactional
    public void withdraw(Account account, Double amount, String description) {
        account.setAccountBalance(account.getAccountBalance().subtract(new BigDecimal(amount)));
        accountRepository.save(account);

        Date date = new Date();

        Transaction transaction = new Transaction();
        transaction.setAccount(account);
        transaction.setDescription(description);
        transaction.setType("Withdraw");
        transaction.setStatus("Finished");
        transaction.setAmount(amount);
        transaction.setAvailableBalance(account.getAccountBalance());
        transaction.setDate(date);
        transactionRepository.save(transaction);
    }

    @Transactional
    public void transferFromTo(Account from, Account to, Double amount) {
        withdraw(from, amount, "Transfer to " + to.getAccountNumber());
        deposit(to, amount, "Transfer from " + from.getAccountNumber());
    }

    public void toSomeoneElseTransfer(Recipient recipient, Account from, Double amount) {
        Account to = accountRepository.findByAccountNumber(recipient.getAccountNumber());
        if (to == null) {
            log.warn("Can't find account with number: " + recipient.getAccountNumber());
            return;
        }
        transferFromTo(from, to, amount);
    }
}
