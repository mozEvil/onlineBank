package ru.mozevil.bank.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mozevil.bank.entities.*;
import ru.mozevil.bank.repositories.RoleRepository;
import ru.mozevil.bank.repositories.UserRepository;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final AccountService accountService;

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public boolean checkUsernameExists(String username) {
        return userRepository.findByUsername(username) != null;
    }

    public boolean checkEmailExists(String email) {
        return userRepository.findByEmail(email) != null;
    }

    public boolean checkUserExists(String username, String email) {
        return checkUsernameExists(username) || checkEmailExists(email);
    }

    @Transactional
    public User createUser(User user) {
        String passEncoded = passwordEncoder.encode(user.getPassword());
        user.setPassword(passEncoded);
        Role role = roleRepository.findByName(RoleName.USER.getName());
        user.setRoles(Collections.singleton(role));
        Account primary = accountService.createAccount(user, AccountType.PRIMARY);
        Account savings = accountService.createAccount(user, AccountType.SAVINGS);
        user.setAccounts(Arrays.asList(primary, savings));
        return userRepository.save(user);
    }

}
