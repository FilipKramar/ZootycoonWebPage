package dev.fkramar.zootycoon.Animal.Repository;

import dev.fkramar.zootycoon.Animal.Model.Animal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AnimalRepository extends JpaRepository<Animal,Long> {
    Optional<Animal> findBySpecies(String species);

    List<Animal> findByIdIn(List<Long> animalIds);
}
