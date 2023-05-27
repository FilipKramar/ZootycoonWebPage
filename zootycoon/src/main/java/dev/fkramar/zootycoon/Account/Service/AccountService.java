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
private final AuthenticationManager  authenticationManager;
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

        var account = Account.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .username(request.getUsername())
                .zoos(new ArrayList<>())
                .role(Role.USER)
                .build();

        repository.save(account);

        var jwtToken = jwtService.generateToken(account);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }


    public String delete(AuthenticationRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        var account = repository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        repository.delete(account);

        return("User has been deleted.");
    }
    public AuthenticationResponse authenticate(AuthenticationRequest request) {

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(),request.getPassword()));

        var account=repository.findByUsername(request.getUsername())
                .orElseThrow();
        var jwtToken= jwtService.generateToken(account);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }


    public AuthenticationResponse bindZooToAccount(ZooAdditionRequest request) {
        Zoo zoo = zooRepository.findByName(request.getZooName())
                .orElseThrow(() -> new BindingException("Zoo not found"));

        Account account = repository.findByUsername(request.getUsername())
                .orElseThrow(() -> new BindingException("Account not found"));

        List<Zoo> zoos = account.getZoos();
        zoos.add(zoo);
        account.setZoos(zoos);
        Account updatedAccount = repository.save(account);
        String jwtToken = jwtService.generateToken(updatedAccount);
       return AuthenticationResponse.builder().token(jwtToken).build();
    }

    public ResponseEntity<Account> findAccountById(String username) {
        Account account = repository.findByUsername(username)
                .orElseThrow(() -> new ResourceAccessException("Account not found"));

        return ResponseEntity.ok(account);
    }



}
