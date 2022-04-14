package ru.mozevil.bank.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.mozevil.bank.entities.Account;
import ru.mozevil.bank.entities.Recipient;

import java.util.List;

@Repository
public interface RecipientRepository extends JpaRepository<Recipient, Long> {
    Recipient findByName(String name);

    @Query("SELECT r FROM Recipient r, User u WHERE r.user = u AND u.username = ?1")
    List<Recipient> findRecipientList(String username);

    void deleteByName(String name);

}
