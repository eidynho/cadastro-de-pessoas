package viniciuseidy.cadastro_de_pessoas.modules.person.controllers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import viniciuseidy.cadastro_de_pessoas.exceptions.PersonFoundException;
import viniciuseidy.cadastro_de_pessoas.exceptions.PersonNotFoundException;
import viniciuseidy.cadastro_de_pessoas.modules.contact.entities.ContactEntity;
import viniciuseidy.cadastro_de_pessoas.modules.person.dto.CreatePersonRequestDTO;
import viniciuseidy.cadastro_de_pessoas.modules.person.dto.UpdatePersonRequestDTO;
import viniciuseidy.cadastro_de_pessoas.modules.person.entities.PersonEntity;
import viniciuseidy.cadastro_de_pessoas.modules.person.useCases.CreatePersonUseCase;
import viniciuseidy.cadastro_de_pessoas.modules.person.useCases.DeletePersonUseCase;
import viniciuseidy.cadastro_de_pessoas.modules.person.useCases.GetFilteredPersonsUseCase;
import viniciuseidy.cadastro_de_pessoas.modules.person.useCases.GetPersonByCPFUseCase;
import viniciuseidy.cadastro_de_pessoas.modules.person.useCases.GetPersonByIdUseCase;
import viniciuseidy.cadastro_de_pessoas.modules.person.useCases.UpdatePersonUseCase;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/person")
public class PersonController {

    @Autowired
    private GetPersonByIdUseCase getPersonByIdUseCase;

    @Autowired
    private GetPersonByCPFUseCase getPersonByCPFUseCase;

    @Autowired
    private GetFilteredPersonsUseCase getFilteredPersonsUseCase;

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

    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<Object> getByCPF(@PathVariable String cpf) {
        try {
            PersonEntity personEntity = this.getPersonByCPFUseCase.execute(cpf);

            return ResponseEntity.ok().body(personEntity);
        } catch (PersonNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<Object> getPersons(
        @RequestParam Optional<String> name,
        @RequestParam Optional<String> cpf,
        @RequestParam Optional<String> birthDateStr,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int perPage
    ) {
        Optional<LocalDate> birthDate = birthDateStr.map(dateStr -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            return LocalDate.parse(dateStr, formatter);
        });

        Page<PersonEntity> personsPage = this.getFilteredPersonsUseCase.execute(name, cpf, birthDate, page, perPage);
        
        return ResponseEntity.ok().body(personsPage);
    }
    
    @PostMapping
    public ResponseEntity<Object> create(@Valid @RequestBody CreatePersonRequestDTO data) {
        try {
            PersonEntity personEntity = new PersonEntity(data);

            ContactEntity contactEntity = new ContactEntity();
            contactEntity.setName(data.contactName());
            contactEntity.setPhone(data.contactPhone());
            contactEntity.setEmail(data.contactEmail());

            var createdPerson = this.createPersonUseCase.execute(personEntity, contactEntity);
            
            return ResponseEntity.ok().body(createdPerson);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (PersonFoundException e) {
            return ResponseEntity.notFound().build();
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
