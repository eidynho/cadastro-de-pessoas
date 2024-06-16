package viniciuseidy.cadastro_de_pessoas.modules.person.useCases;

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

import viniciuseidy.cadastro_de_pessoas.exceptions.PersonNotFoundException;
import viniciuseidy.cadastro_de_pessoas.modules.person.entities.PersonEntity;
import viniciuseidy.cadastro_de_pessoas.modules.person.repositories.PersonRepository;

public class DeletePersonUseCaseTest {

    @Mock
    private PersonRepository personRepository;

    @Autowired
    @InjectMocks
    private DeletePersonUseCase deletePersonUseCase;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }
    
    @Test
    @DisplayName("Should delete when everything is ok")
    void deletePersonUseCaseSuccess() {
        UUID personId = UUID.randomUUID();

        PersonEntity personEntity = new PersonEntity();
        personEntity.setId(personId);

        when(this.personRepository.findById(personId)).thenReturn(Optional.of(personEntity));

        this.deletePersonUseCase.execute(personId);

        verify(this.personRepository).deleteById(personId);
    }

    @Test
    @DisplayName("Should not delete a person when person does not exists")
    void deletePersonUseCaseErrorCase1() {
        UUID personId = UUID.randomUUID();

        when(this.personRepository.findById(personId)).thenReturn(Optional.empty());

        assertThrows(PersonNotFoundException.class, () -> {
            this.deletePersonUseCase.execute(personId);
        });

        verify(this.personRepository, never()).deleteById(personId);
    }
}
