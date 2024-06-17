package viniciuseidy.cadastro_de_pessoas.modules.contact.useCases;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.never;
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
import viniciuseidy.cadastro_de_pessoas.modules.contact.entities.ContactEntity;
import viniciuseidy.cadastro_de_pessoas.modules.contact.repositories.ContactRepository;

public class DeleteContactUseCaseTest {
    
    @Mock
    private ContactRepository contactRepository;

    @Autowired
    @InjectMocks
    private DeleteContactUseCase deleteContactUseCase;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should delete a contact when everything is ok")
    public void deleteContactUseCaseSuccess() throws ContactNotFoundException {
        UUID contactId = UUID.randomUUID();

        ContactEntity contactToDelete = new ContactEntity();
        contactToDelete.setId(contactId);

        when(this.contactRepository.findById(contactId)).thenReturn(Optional.of(contactToDelete));

        this.deleteContactUseCase.execute(contactId);

        verify(this.contactRepository).deleteById(contactId);
    }

    @Test
    @DisplayName("Should return ContactNotFoundException when contact does not exists")
    public void deleteContactUseCaseError() {
        UUID contactId = UUID.randomUUID();

        when(this.contactRepository.findById(contactId)).thenReturn(Optional.empty());

        assertThrows(ContactNotFoundException.class, () -> {
            this.deleteContactUseCase.execute(contactId);
        });

        verify(this.contactRepository, never()).deleteById(contactId);
    }
}
