package dev.fkramar.zootycoon.Account.Controller;

import dev.fkramar.zootycoon.Account.DTO.AuthenticationRequest;
import dev.fkramar.zootycoon.Account.DTO.RegisterRequest;
import dev.fkramar.zootycoon.Account.Model.Account;
import dev.fkramar.zootycoon.Account.Service.AccountService;
import dev.fkramar.zootycoon.Zoo.Model.Zoo;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/account")
@AllArgsConstructor
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class AccountController {

    private final AccountService service;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> registerAccount (@RequestBody RegisterRequest request){
        return ResponseEntity.ok(service.register(request));

    }

    @PutMapping
    public ResponseEntity<AuthenticationResponse> updateAccount (@RequestBody RegisterRequest request){
        return ResponseEntity.ok(service.updateAccount(request));

    }
    @DeleteMapping("/{username}")
    public ResponseEntity<String> deleteAccount (@PathVariable("username") String username){
        return ResponseEntity.ok(service.delete(username));

    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticateAccount(@RequestBody AuthenticationRequest request){
        return ResponseEntity.ok(service.authenticate(request));
    }



    @GetMapping("/{username}")
    public ResponseEntity<Account> getAccountById(@PathVariable("username") String username) {
        return service.findAccountById(username);
    }

    @GetMapping("/getzoo/{username}")
    public ResponseEntity<List<Zoo>> getAccountZoo(@PathVariable("username") String username) {
        return  ResponseEntity.ok(service.findAccountZoos(username));
    }



}
