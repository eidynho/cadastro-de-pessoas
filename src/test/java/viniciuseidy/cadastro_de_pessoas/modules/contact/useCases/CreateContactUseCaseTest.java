package viniciuseidy.cadastro_de_pessoas.modules.contact.useCases;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import viniciuseidy.cadastro_de_pessoas.exceptions.InvalidEmailException;
import viniciuseidy.cadastro_de_pessoas.exceptions.PersonNotFoundException;
import viniciuseidy.cadastro_de_pessoas.modules.contact.entities.ContactEntity;
import viniciuseidy.cadastro_de_pessoas.modules.contact.repositories.ContactRepository;
import viniciuseidy.cadastro_de_pessoas.modules.person.entities.PersonEntity;
import viniciuseidy.cadastro_de_pessoas.modules.person.repositories.PersonRepository;

public class CreateContactUseCaseTest {
    
    @Mock
    private ContactRepository contactRepository;

    @Mock
    private PersonRepository personRepository;

    @Autowired
    @InjectMocks
    private CreateContactUseCase createContactUseCase;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should create a contact when everything is ok")
    void createContactUseCaseSuccess() {
        UUID id = UUID.randomUUID();

        PersonEntity person = new PersonEntity();
        person.setId(id);

        when(this.personRepository.findById(id)).thenReturn(Optional.of(person));

        ContactEntity contact = new ContactEntity();
        contact.setPerson(person);
        contact.setName("Contact name");
        contact.setPhone("44999999999");
        contact.setEmail("email@email.com");

        when(this.contactRepository.save(contact)).thenReturn(contact);

        var result = this.createContactUseCase.execute(id, contact);

        verify(this.contactRepository).save(contact);
        assertNotNull(result);
    }

    @Test
    @DisplayName("Should return PersonNotFoundException when person does not exists")
    void createContactUseCaseError1() {
        UUID id = UUID.randomUUID();

        when(this.personRepository.findById(id)).thenReturn(Optional.empty());

        ContactEntity contact = new ContactEntity();

        assertThrows(PersonNotFoundException.class, () -> {
            this.createContactUseCase.execute(id, contact);
        });
    }

    @Test
    @DisplayName("Should return InvalidEmailException when contact email is invalid")
    void createContactUseCaseError2() {
        UUID id = UUID.randomUUID();

        PersonEntity person = new PersonEntity();
        person.setId(id);

        when(this.personRepository.findById(id)).thenReturn(Optional.of(person));

        ContactEntity contact = new ContactEntity();
        contact.setEmail("invalid-email");

        assertThrows(InvalidEmailException.class, () -> {
            this.createContactUseCase.execute(id, contact);
        });
    }
}
