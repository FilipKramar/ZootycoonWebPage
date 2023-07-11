package dev.fkramar.zootycoon.Animal.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class  AnimalCreateRequest {
    private String species;
    private String biome;
    private String diet;
    private String picture;
}
