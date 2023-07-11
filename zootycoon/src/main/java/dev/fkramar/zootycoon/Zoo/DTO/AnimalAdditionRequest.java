package dev.fkramar.zootycoon.Zoo.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AnimalAdditionRequest {
    private String zooName;
    private String species;
}
