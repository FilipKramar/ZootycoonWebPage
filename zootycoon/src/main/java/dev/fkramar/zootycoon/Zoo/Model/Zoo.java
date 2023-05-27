package dev.fkramar.zootycoon.Zoo.Model;

import dev.fkramar.zootycoon.Animal.Model.Animal;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "zoo")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Zoo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String biome;
    private List<String> pictures;


    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "zoo_animal",
            joinColumns = @JoinColumn(name = "zoo_id"),
            inverseJoinColumns = @JoinColumn(name = "animal_id")
    )
    private List<Animal> animals;

}
