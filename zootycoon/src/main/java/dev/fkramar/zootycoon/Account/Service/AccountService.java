package dev.fkramar.zootycoon.Account.Service;

import dev.fkramar.zootycoon.Account.Controller.AuthenticationResponse;
import dev.fkramar.zootycoon.Account.DTO.AuthenticationRequest;
import dev.fkramar.zootycoon.Account.DTO.RegisterRequest;
import dev.fkramar.zootycoon.Account.DTO.ZooAdditionRequest;
import dev.fkramar.zootycoon.Account.Model.Account;
import dev.fkramar.zootycoon.Account.Model.Role;
import dev.fkramar.zootycoon.Account.Repository.AccountRepository;
import dev.fkramar.zootycoon.Exceptions.BindingException;
import dev.fkramar.zootycoon.Exceptions.RegistrationException;
import dev.fkramar.zootycoon.Zoo.DTO.ZooDelete;
import dev.fkramar.zootycoon.Zoo.Model.Zoo;
import dev.fkramar.zootycoon.Zoo.Repository.ZooRepository;
import dev.fkramar.zootycoon.config.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final ZooRepository zooRepository;

    public AuthenticationResponse register(RegisterRequest request) {
        // Check if the username is already in use
        if (repository.existsByUsername(request.getUsername())) {
            throw new RegistrationException("Username is already taken");
        }

        // Check if the email is already in use
        if (repository.existsByEmail(request.getEmail())) {
            throw new RegistrationException("Email is already registered");
        }

        var account = Account.builder().firstName(request.getFirstName()).lastName(request.getLastName()).email(request.getEmail()).password(passwordEncoder.encode(request.getPassword())).username(request.getUsername()).zoos(new ArrayList<>()).role(Role.USER).build();

        repository.save(account);

        var jwtToken = jwtService.generateToken(account);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }


    public String delete(String username) {
        var account = repository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));


        List<Zoo> zoos = account.getZoos();


        for (Zoo zoo : zoos) {
            zoo.getAnimals().clear();
        }

        repository.delete(account);

        return "User and associated zoos have been deleted.";
    }


    public AuthenticationResponse authenticate(AuthenticationRequest request) {

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        var account = repository.findByUsername(request.getUsername()).orElseThrow();
        var jwtToken = jwtService.generateToken(account);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }


    public AuthenticationResponse bindZooToAccount(ZooAdditionRequest request) {
        Zoo zoo = zooRepository.findByName(request.getZooName()).orElseThrow(() -> new BindingException("Zoo not found"));

        Account account = repository.findByUsername(request.getUsername()).orElseThrow(() -> new BindingException("Account not found"));

        List<Zoo> zoos = account.getZoos();
        zoos.add(zoo);
        account.setZoos(zoos);
        Account updatedAccount = repository.save(account);
        String jwtToken = jwtService.generateToken(updatedAccount);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }

    public ResponseEntity<Account> findAccountById(String username) {
        Account account = repository.findByUsername(username).orElseThrow(() -> new ResourceAccessException("Account not found"));

        return ResponseEntity.ok(account);
    }

    public AuthenticationResponse removeZooFromAccount(ZooDelete request) {
        Zoo zoo = zooRepository.findByName(request.getName()).orElseThrow(() -> new BindingException("Zoo not found"));

        Account account = repository.findByUsername(request.getUsername()).orElseThrow(() -> new BindingException("Account not found"));

        List<Zoo> zoos = account.getZoos();
        zoos.remove(zoo);
        account.setZoos(zoos);
        Account updatedAccount = repository.save(account);
        String jwtToken = jwtService.generateToken(updatedAccount);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }


    public List<Zoo> findAccountZoos(String username) {

        Account account = repository.findByUsername(username).orElseThrow(() -> new ResourceAccessException("Account not found"));

        return account.getZoos();
    }

    public AuthenticationResponse updateAccount(RegisterRequest request) {
        Account account = repository.findByEmail(request.getEmail()).orElseThrow(() -> new RuntimeException("Account not found with email: " + request.getEmail()));

        if (request.getFirstName() != null) {
            account.setFirstName(request.getFirstName());
        }
        if (request.getLastName() != null) {
            account.setLastName(request.getLastName());
        }
        if (request.getPassword() != null && !request.getPassword().trim().isEmpty()) {
            account.setPassword(passwordEncoder.encode(request.getPassword()));
        }
        if (request.getUsername() != null) {
            account.setUsername(request.getUsername());
        }


        repository.save(account);


        var jwtToken = jwtService.generateToken(account);


        return AuthenticationResponse.builder().token(jwtToken).build();
    }

}
