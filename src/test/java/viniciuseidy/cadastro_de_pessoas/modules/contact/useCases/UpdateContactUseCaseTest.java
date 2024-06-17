package viniciuseidy.cadastro_de_pessoas.modules.contact.useCases;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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

import viniciuseidy.cadastro_de_pessoas.exceptions.ContactNotFoundException;
import viniciuseidy.cadastro_de_pessoas.exceptions.InvalidEmailException;
import viniciuseidy.cadastro_de_pessoas.modules.contact.entities.ContactEntity;
import viniciuseidy.cadastro_de_pessoas.modules.contact.repositories.ContactRepository;

public class UpdateContactUseCaseTest {
    
    @Mock
    private ContactRepository contactRepository;

    @Autowired
    @InjectMocks
    private UpdateContactUseCase updateContactUseCase;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should update contact when everything is ok")
    void updateContactUseCaseSuccess() throws ContactNotFoundException, InvalidEmailException {
        UUID id = UUID.randomUUID();

        ContactEntity existentContact = new ContactEntity();
        existentContact.setId(id);

        when(this.contactRepository.findById(id)).thenReturn(Optional.of(existentContact));

        ContactEntity contactToUpdate = new ContactEntity();
        contactToUpdate.setId(id);
        contactToUpdate.setName("Vinicius");
        contactToUpdate.setPhone("44 999999999");
        contactToUpdate.setEmail("email@email.com");

        when(this.contactRepository.save(contactToUpdate)).thenReturn(contactToUpdate);

        var result = this.updateContactUseCase.execute(contactToUpdate);

        verify(this.contactRepository).save(contactToUpdate);
        assertNotNull(result);
        assertThat(result.getName().equals(contactToUpdate.getName()));
        assertThat(result.getPhone().equals(contactToUpdate.getPhone()));
        assertThat(result.getEmail().equals(contactToUpdate.getEmail()));
    }

    @Test
    @DisplayName("Should return ContactNotFoundException when contact does not exists")
    void updateContactUseCaseError1() {
        UUID id = UUID.randomUUID();

        ContactEntity existentContact = new ContactEntity();
        existentContact.setId(id);

        when(this.contactRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ContactNotFoundException.class, () -> {
            this.updateContactUseCase.execute(existentContact);
        });
    }

    @Test
    @DisplayName("Should return InvalidEmailException when email is invalid")
    void updateContactUseCaseError2() {
        UUID id = UUID.randomUUID();

        ContactEntity existentContact = new ContactEntity();
        existentContact.setId(id);
        existentContact.setEmail("invalid-email");

        when(this.contactRepository.findById(id)).thenReturn(Optional.of(existentContact));

        assertThrows(InvalidEmailException.class, () -> {
            this.updateContactUseCase.execute(existentContact);
        });
    }
}
