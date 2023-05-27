package dev.fkramar.zootycoon.Zoo.Service;


import dev.fkramar.zootycoon.Account.DTO.ZooAdditionRequest;
import dev.fkramar.zootycoon.Account.Model.Account;
import dev.fkramar.zootycoon.Account.Repository.AccountRepository;
import dev.fkramar.zootycoon.Account.Service.AccountService;
import dev.fkramar.zootycoon.Animal.Model.Animal;
import dev.fkramar.zootycoon.Animal.Repository.AnimalRepository;
import dev.fkramar.zootycoon.Exceptions.BindingException;
import dev.fkramar.zootycoon.Exceptions.DeletingException;
import dev.fkramar.zootycoon.Exceptions.RegistrationException;
import dev.fkramar.zootycoon.Zoo.DTO.AnimalAdditionRequest;
import dev.fkramar.zootycoon.Zoo.DTO.ZooCreateRequest;
import dev.fkramar.zootycoon.Zoo.DTO.ZooDelete;
import dev.fkramar.zootycoon.Zoo.Model.Zoo;
import dev.fkramar.zootycoon.Zoo.Repository.ZooRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;



@Service
@RequiredArgsConstructor
public class ZooService{

    private final ZooRepository zooRepository;
    private  final AccountService accountService;
    private final AnimalRepository animalRepository;
    private final AccountRepository accountRepository;

    public Zoo create(ZooCreateRequest request) {
        if (zooRepository.existsByName(request.getName())) {
            throw new RegistrationException("Zoo name is already in use");
        }


        Zoo zoo = new Zoo();
        zoo.setName(request.getName());
        zoo.setBiome(request.getBiome());
        zoo.setPictures(request.getPictures());
        zoo.setAnimals(request.getAnimals());

        zooRepository.save(zoo);

            ZooAdditionRequest zooAdditionRequest= new ZooAdditionRequest();
            zooAdditionRequest.setZooName(request.getName());
            zooAdditionRequest.setUsername(request.getUsername());
             accountService.bindZooToAccount(zooAdditionRequest);


        return zoo;
    }

    public String delete(ZooDelete request) {
        var zoo = zooRepository.findByName(request.getName())
                .orElseThrow(() -> new RuntimeException("Zoo not found"));
        zoo.getAnimals().clear();
        zooRepository.delete(zoo);

        return("Zoo has been deleted.");
    }

    public void bindZooAndAnimal(AnimalAdditionRequest request) {
        Zoo zoo = zooRepository.findByName(request.getZooName())
                .orElseThrow(() -> new BindingException("Zoo not found"));

        Animal animal = animalRepository.findBySpecies(request.getSpecies())
                .orElseThrow(() -> new BindingException("Animal not found"));

        zoo.getAnimals().add(animal);
        zooRepository.save(zoo);
        Account account = accountRepository.findByZoosContaining(zoo);
        accountRepository.save(account);

    }
    public void deleteAnimalFromZoo(AnimalAdditionRequest request) {
        Zoo zoo = zooRepository.findByName(request.getZooName())
                .orElseThrow(() -> new DeletingException("Zoo not found"));

        Animal animal = animalRepository.findBySpecies(request.getSpecies())
                .orElseThrow(() -> new DeletingException("Animal not found"));

        zoo.getAnimals().remove(animal);
        zooRepository.save(zoo);

        Account account = accountRepository.findByZoosContaining(zoo);
        accountRepository.save(account);

    }

}
