package dev.fkramar.zootycoon.Zoo.Controller;
import dev.fkramar.zootycoon.Zoo.DTO.AnimalAdditionRequest;
import dev.fkramar.zootycoon.Zoo.DTO.ZooCreateRequest;
import dev.fkramar.zootycoon.Zoo.DTO.ZooDelete;
import dev.fkramar.zootycoon.Zoo.Model.Zoo;
import dev.fkramar.zootycoon.Zoo.Service.ZooService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/account/zoo")
@AllArgsConstructor
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class ZooController {

    private final ZooService zooService;

    @PostMapping("/create")
    public ResponseEntity<Zoo> createAZoo (@RequestBody ZooCreateRequest request){
        return ResponseEntity.ok(zooService.create(request));

    }
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteaZoo (@RequestBody ZooDelete request){
        return ResponseEntity.ok(zooService.delete(request));

    }
    @DeleteMapping("/delete/animal")
    public ResponseEntity<String> deleteAnAnimal(@RequestBody AnimalAdditionRequest request) {
        zooService.deleteAnimalFromZoo(request);
        return ResponseEntity.ok("Animal deleted from zoo successfully");
    }

    @PutMapping()
    public ResponseEntity<String> editZooAnimalList(@RequestBody AnimalAdditionRequest request) {
        zooService.deleteAnimalFromZoo(request);
        return ResponseEntity.ok("Animal deleted from zoo successfully");
    }
}
