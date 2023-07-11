package dev.fkramar.zootycoon.Animal.Controller;

import dev.fkramar.zootycoon.Animal.DTO.AnimalCreateRequest;
import dev.fkramar.zootycoon.Animal.Model.Animal;
import dev.fkramar.zootycoon.Animal.Service.AnimalService;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/animals")
@AllArgsConstructor
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class AnimalController {
    private  final AnimalService animalService;

    @PostMapping("/create")
    public ResponseEntity<Animal> createAnAnimal (@RequestBody AnimalCreateRequest request){
        return ResponseEntity.ok(animalService.create(request));

    }


    @GetMapping
    public List<Animal> getAllAnimals(){

        return animalService.getallAnimals();
    }

    @GetMapping("/{name}")
    public Optional<Animal> getAnAnimals(@PathVariable String name){

        return animalService.getAnAnimal(name);
    }



}
