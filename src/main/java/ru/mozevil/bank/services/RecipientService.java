package ru.mozevil.bank.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mozevil.bank.entities.Recipient;
import ru.mozevil.bank.repositories.RecipientRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecipientService {
    private final RecipientRepository recipientRepository;

    public Recipient findRecipientByName(String recipientName) {
        return recipientRepository.findByName(recipientName);
    }

    public void saveRecipient(Recipient recipient) {
        recipientRepository.save(recipient);
    }

    public List<Recipient> findRecipientList(String username) {
        return recipientRepository.findRecipientList(username);
    }

    public void deleteRecipientByName(String recipientName) {
        recipientRepository.deleteByName(recipientName);
    }
}
