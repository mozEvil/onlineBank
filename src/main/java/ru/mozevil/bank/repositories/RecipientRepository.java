package ru.mozevil.bank.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mozevil.bank.entities.Recipient;

@Repository
public interface RecipientRepository extends JpaRepository<Recipient, Long> {
}
