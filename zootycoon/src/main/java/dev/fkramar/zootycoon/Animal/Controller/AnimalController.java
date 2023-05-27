package dev.fkramar.zootycoon.Animal.Controller;

import dev.fkramar.zootycoon.Animal.DTO.AnimalCreateRequest;
import dev.fkramar.zootycoon.Animal.Model.Animal;
import dev.fkramar.zootycoon.Animal.Service.AnimalService;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/animals")
@AllArgsConstructor
public class AnimalController {
    private  final AnimalService animalService;

    @PostMapping("/create")
    public ResponseEntity<Animal> createAnAnimal (@RequestBody AnimalCreateRequest request){
        return ResponseEntity.ok(animalService.create(request));

    }



}
