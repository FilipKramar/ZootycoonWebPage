package dev.fkramar.zootycoon.Zoo.Repository;

import dev.fkramar.zootycoon.Account.Model.Account;
import dev.fkramar.zootycoon.Zoo.Model.Zoo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ZooRepository  extends JpaRepository<Zoo,Long> {


    boolean existsByName(String name);

    Optional<Zoo> findByName(String zooName);
}
