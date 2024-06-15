package viniciuseidy.cadastro_de_pessoas.modules.person.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import viniciuseidy.cadastro_de_pessoas.modules.person.dto.PersonRequestDTO;
import viniciuseidy.cadastro_de_pessoas.modules.person.entities.PersonEntity;
import viniciuseidy.cadastro_de_pessoas.modules.person.useCases.CreatePersonUseCase;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/person")
public class PersonController {

    @Autowired
    private CreatePersonUseCase createPersonUseCase;
    
    @PostMapping
    public ResponseEntity<Object> create(@Valid @RequestBody PersonRequestDTO data) {
        try {
            PersonEntity personEntity = new PersonEntity(data);

            var createdPerson = this.createPersonUseCase.execute(personEntity);
            return ResponseEntity.ok().body(createdPerson);
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
