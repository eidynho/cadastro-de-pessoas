package viniciuseidy.cadastro_de_pessoas.modules.contact.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import viniciuseidy.cadastro_de_pessoas.exceptions.ContactNotFoundException;
import viniciuseidy.cadastro_de_pessoas.exceptions.InvalidEmailException;
import viniciuseidy.cadastro_de_pessoas.exceptions.PersonFoundException;
import viniciuseidy.cadastro_de_pessoas.exceptions.PersonNotFoundException;
import viniciuseidy.cadastro_de_pessoas.modules.contact.dto.CreateContactRequestDTO;
import viniciuseidy.cadastro_de_pessoas.modules.contact.dto.UpdateContactRequestDTO;
import viniciuseidy.cadastro_de_pessoas.modules.contact.entities.ContactEntity;
import viniciuseidy.cadastro_de_pessoas.modules.contact.useCases.CreateContactUseCase;
import viniciuseidy.cadastro_de_pessoas.modules.contact.useCases.DeleteContactUseCase;
import viniciuseidy.cadastro_de_pessoas.modules.contact.useCases.UpdateContactUseCase;

@RestController
@RequestMapping("/contact")
public class ContactController {

    @Autowired
    private CreateContactUseCase createContactUseCase;

    @Autowired
    private UpdateContactUseCase updateContactUseCase;

    @Autowired
    private DeleteContactUseCase deleteContactUseCase;
    
    @PostMapping
    public ResponseEntity<Object> create(@Valid @RequestBody CreateContactRequestDTO data) {
        try {
            ContactEntity contact = new ContactEntity(data);

            var createdContact = this.createContactUseCase.execute(data.personId(), contact);

            return ResponseEntity.ok().body(createdContact);
        } catch (PersonFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (InvalidEmailException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity<Object> update(@Valid @RequestBody UpdateContactRequestDTO data) {
        try {
            ContactEntity contact = new ContactEntity(data);

            var updatedContact = this.updateContactUseCase.execute(contact);

            return ResponseEntity.ok().body(updatedContact);
        } catch (ContactNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (InvalidEmailException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable UUID id) {
        try {
            this.deleteContactUseCase.execute(id);

            return ResponseEntity.noContent().build();
        } catch (ContactNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
