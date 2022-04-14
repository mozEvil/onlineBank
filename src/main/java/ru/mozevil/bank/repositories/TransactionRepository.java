package ru.mozevil.bank.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mozevil.bank.entities.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
