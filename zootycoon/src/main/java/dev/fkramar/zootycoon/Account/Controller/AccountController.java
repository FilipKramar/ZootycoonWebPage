package dev.fkramar.zootycoon.Account.Controller;

import dev.fkramar.zootycoon.Account.DTO.AuthenticationRequest;
import dev.fkramar.zootycoon.Account.DTO.RegisterRequest;
import dev.fkramar.zootycoon.Account.DTO.ZooAdditionRequest;
import dev.fkramar.zootycoon.Account.Model.Account;
import dev.fkramar.zootycoon.Account.Service.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/account")
@AllArgsConstructor
public class AccountController {

    private final AccountService service;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> registerAccount (@RequestBody RegisterRequest request){
        return ResponseEntity.ok(service.register(request));

    }
    @PostMapping("/delete")
    public ResponseEntity<String> deleteAccount (@RequestBody AuthenticationRequest request){
        return ResponseEntity.ok(service.delete(request));

    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> registerAccount (@RequestBody AuthenticationRequest request){
        return ResponseEntity.ok(service.authenticate(request));
    }

    @PostMapping("/createazoo")
    public ResponseEntity<AuthenticationResponse> createAZoo (@RequestBody ZooAdditionRequest request){
        return ResponseEntity.ok(service.bindZooToAccount(request));
    }

    @GetMapping("/{username}")
    public ResponseEntity<Account> getAccountById(@PathVariable("username") String username) {
        return service.findAccountById(username);
    }


}
