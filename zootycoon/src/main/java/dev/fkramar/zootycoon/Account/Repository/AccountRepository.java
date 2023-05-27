package dev.fkramar.zootycoon.Account.Repository;

import dev.fkramar.zootycoon.Account.Model.Account;
import dev.fkramar.zootycoon.Zoo.Model.Zoo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account,Long> {
    Optional<Account> findByUsername(String username);
    public Optional<Account> findAccountById(Long accountId);


    Account deleteByUsername(String password);

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

    Account findByZoosContaining(Zoo zoo);
}
