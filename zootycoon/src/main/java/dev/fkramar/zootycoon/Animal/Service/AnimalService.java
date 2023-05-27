package dev.fkramar.zootycoon.Animal.Service;


import dev.fkramar.zootycoon.Animal.DTO.AnimalCreateRequest;
import dev.fkramar.zootycoon.Animal.Model.Animal;
import dev.fkramar.zootycoon.Animal.Repository.AnimalRepository;

import dev.fkramar.zootycoon.Zoo.DTO.AnimalAdditionRequest;

import dev.fkramar.zootycoon.Zoo.Service.ZooService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AnimalService {

    private final AnimalRepository animalRepository;
    private final ZooService zooService;

    public Animal create(AnimalCreateRequest request) {
        Animal animal=new Animal();
        animal.setSpecies(request.getSpecies());
        animal.setBiome(request.getBiome());
        animal.setPicture(request.getPicture());
        animal.setDiet(request.getDiet());

        animalRepository.save(animal);

        AnimalAdditionRequest animalAdditionRequest= new AnimalAdditionRequest();
        animalAdditionRequest.setZooName(request.getZooName());
        animalAdditionRequest.setZooName(request.getSpecies());
        zooService.bindZooAndAnimal(animalAdditionRequest);


        return animal;
    }

}
