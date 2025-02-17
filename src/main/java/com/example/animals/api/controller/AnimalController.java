package com.example.animals.api.controller;

import com.example.animals.api.model.Animal;
import com.example.animals.api.repository.AnimalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/animals")
public class AnimalController {

    @Autowired
    private AnimalRepository animalRepository;

    @GetMapping
    public List<Animal> list() {
        return animalRepository.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Animal add(@RequestBody Animal animal){
        return animalRepository.save(animal);
    }

    @GetMapping(path = {"/{id}"})
    public ResponseEntity findById(@PathVariable long id){
        return animalRepository.findById(id)
                .map(record -> ResponseEntity.ok().body(record))
                .orElse(ResponseEntity.notFound().build());
    }


    @PutMapping(value="/{id}")
    public ResponseEntity update(@PathVariable("id") long id,
                                 @RequestBody Animal animal) {
        return animalRepository.findById(id)
                .map(animal1 -> {
                    animal1.setAnimalDescription(animal.getAnimalDescription());
                    animal1.setAnimalClass(animal.getAnimalClass());
                    animal1.setAnimalGroup(animal.getAnimalGroup());
                    Animal updated = animalRepository.save(animal1);
                    return ResponseEntity.ok().body(updated);
                }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping(path ={"/{id}"})
    public ResponseEntity <?> delete(@PathVariable long id) {
        return animalRepository.findById(id)
                .map(animal1 -> {
                    animalRepository.deleteById(id);
                    return ResponseEntity.ok().build();
                }).orElse(ResponseEntity.notFound().build());
    }
}
