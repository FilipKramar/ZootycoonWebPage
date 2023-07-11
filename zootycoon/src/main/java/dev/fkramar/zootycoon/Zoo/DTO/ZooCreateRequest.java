package dev.fkramar.zootycoon.Zoo.DTO;

import dev.fkramar.zootycoon.Animal.Model.Animal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ZooCreateRequest {
    private String username;
    private String name;
    private String biome;
    private List<String> pictures;
    private List<Long> animals;

}
