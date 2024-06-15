package viniciuseidy.cadastro_de_pessoas.modules.person.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import viniciuseidy.cadastro_de_pessoas.modules.person.dto.CreatePersonRequestDTO;
import viniciuseidy.cadastro_de_pessoas.modules.person.dto.UpdatePersonRequestDTO;
import viniciuseidy.cadastro_de_pessoas.modules.person.entities.PersonEntity;
import viniciuseidy.cadastro_de_pessoas.modules.person.useCases.CreatePersonUseCase;
import viniciuseidy.cadastro_de_pessoas.modules.person.useCases.UpdatePersonUseCase;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/person")
public class PersonController {

    @Autowired
    private CreatePersonUseCase createPersonUseCase;

    @Autowired
    private UpdatePersonUseCase updatePersonUseCase;
    
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
}
