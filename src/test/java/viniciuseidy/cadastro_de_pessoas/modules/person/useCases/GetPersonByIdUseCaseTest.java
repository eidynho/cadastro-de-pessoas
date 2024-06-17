package viniciuseidy.cadastro_de_pessoas.modules.person.useCases;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

import viniciuseidy.cadastro_de_pessoas.exceptions.PersonNotFoundException;
import viniciuseidy.cadastro_de_pessoas.modules.person.entities.PersonEntity;
import viniciuseidy.cadastro_de_pessoas.modules.person.repositories.PersonRepository;

public class GetPersonByIdUseCaseTest {

    @Mock
    private PersonRepository personRepository;

    @Autowired
    @InjectMocks
    private GetPersonByIdUseCase getPersonByIdUseCase;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should get a person by id when everything is ok")
    void getPersonByIdUseCaseSuccess() throws PersonNotFoundException {
        UUID id = UUID.randomUUID();

        PersonEntity personEntity = new PersonEntity();
        personEntity.setId(id);

        when(this.personRepository.findById(id)).thenReturn(Optional.of(personEntity));

        PersonEntity result = this.getPersonByIdUseCase.execute(id);

        assertEquals(id, personEntity.getId());
        verify(this.personRepository).findById(id);
        assertNotNull(result);
    }

    @Test
    @DisplayName("Should return PersonNotFoundException when person id not exists")
    void getPersonByIdUseCaseError() {
        UUID id = UUID.randomUUID();

        PersonEntity personEntity = new PersonEntity();
        personEntity.setId(id);

        when(this.personRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(PersonNotFoundException.class, () -> {
            this.getPersonByIdUseCase.execute(id);
        });
    }
}
