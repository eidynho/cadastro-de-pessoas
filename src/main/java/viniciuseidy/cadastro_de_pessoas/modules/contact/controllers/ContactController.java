package viniciuseidy.cadastro_de_pessoas.modules.contact.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import viniciuseidy.cadastro_de_pessoas.exceptions.InvalidEmailException;
import viniciuseidy.cadastro_de_pessoas.exceptions.PersonNotFoundException;
import viniciuseidy.cadastro_de_pessoas.modules.contact.dto.CreateContactRequestDTO;
import viniciuseidy.cadastro_de_pessoas.modules.contact.entities.ContactEntity;
import viniciuseidy.cadastro_de_pessoas.modules.contact.useCases.CreateContactUseCase;

@RestController
@RequestMapping("/contact")
public class ContactController {

    @Autowired
    private CreateContactUseCase createContactUseCase;
    
    @PostMapping
    public ResponseEntity<Object> create(@Valid @RequestBody CreateContactRequestDTO data) {
        try {
            ContactEntity contact = new ContactEntity(data);

            var createdContact = this.createContactUseCase.execute(data.personId(), contact);

            return ResponseEntity.ok().body(createdContact);
        } catch (PersonNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (InvalidEmailException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
