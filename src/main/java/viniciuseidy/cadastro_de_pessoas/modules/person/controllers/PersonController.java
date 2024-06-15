package viniciuseidy.cadastro_de_pessoas.modules.person.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import viniciuseidy.cadastro_de_pessoas.exceptions.PersonNotFoundException;
import viniciuseidy.cadastro_de_pessoas.modules.person.dto.CreatePersonRequestDTO;
import viniciuseidy.cadastro_de_pessoas.modules.person.dto.UpdatePersonRequestDTO;
import viniciuseidy.cadastro_de_pessoas.modules.person.entities.PersonEntity;
import viniciuseidy.cadastro_de_pessoas.modules.person.useCases.CreatePersonUseCase;
import viniciuseidy.cadastro_de_pessoas.modules.person.useCases.DeletePersonUseCase;
import viniciuseidy.cadastro_de_pessoas.modules.person.useCases.GetPersonByIdUseCase;
import viniciuseidy.cadastro_de_pessoas.modules.person.useCases.UpdatePersonUseCase;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/person")
public class PersonController {

    @Autowired
    private GetPersonByIdUseCase getPersonByIdUseCase;

    @Autowired
    private CreatePersonUseCase createPersonUseCase;

    @Autowired
    private UpdatePersonUseCase updatePersonUseCase;

    @Autowired
    private DeletePersonUseCase deletePersonUseCase;

    @GetMapping("/id/{id}")
    public ResponseEntity<Object> getById(@PathVariable UUID id) {
        try {
            PersonEntity personEntity = this.getPersonByIdUseCase.execute(id);

            return ResponseEntity.ok().body(personEntity);
        } catch (PersonNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PostMapping
    public ResponseEntity<Object> create(@Valid @RequestBody CreatePersonRequestDTO data) {
        try {
            PersonEntity personEntity = new PersonEntity(data);

            var createdPerson = this.createPersonUseCase.execute(personEntity);
            
            return ResponseEntity.ok().body(createdPerson);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity<Object> update(@Valid @RequestBody UpdatePersonRequestDTO data) {
        try {
            PersonEntity personEntity = new PersonEntity(data);

            var updatedPerson = this.updatePersonUseCase.execute(personEntity);

            return ResponseEntity.ok().body(updatedPerson);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable UUID id) {
        try {
            this.deletePersonUseCase.execute(id);

            return ResponseEntity.noContent().build();
        } catch (PersonNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
