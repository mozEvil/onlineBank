package ru.mozevil.bank.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.mozevil.bank.entities.Account;
import ru.mozevil.bank.entities.AccountType;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    @Query("SELECT a FROM Account a, User u WHERE a.user = u AND u.username = ?1 AND a.type = ?2")
    Account getAccountByUsername(String username, AccountType type);

    Account findByAccountNumber(int accountNumber);
}
