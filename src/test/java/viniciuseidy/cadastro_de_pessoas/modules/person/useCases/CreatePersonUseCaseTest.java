package viniciuseidy.cadastro_de_pessoas.modules.person.useCases;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import viniciuseidy.cadastro_de_pessoas.exceptions.PersonFoundException;
import viniciuseidy.cadastro_de_pessoas.modules.person.entities.PersonEntity;
import viniciuseidy.cadastro_de_pessoas.modules.person.repositories.PersonRepository;

public class CreatePersonUseCaseTest {

    @Mock
    private PersonRepository personRepository;

    @Autowired
    @InjectMocks
    private CreatePersonUseCase createPersonUseCase;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should create a person successfully when everything is ok")
    void createPersonUseCaseSuccess() {
        PersonEntity personEntity = new PersonEntity();
        personEntity.setCpf("12345678901");

        this.createPersonUseCase.execute(personEntity);

        verify(this.personRepository).save(personEntity);
    }

    @Test
    @DisplayName("Should throw an exception when CPF in invalid")
    void createPersonUseCaseErrorCase1() {
        PersonEntity personEntity = new PersonEntity();
        personEntity.setCpf("123456");

        assertThrows(IllegalArgumentException.class, () -> {
            this.createPersonUseCase.execute(personEntity);
        });
    }

    @Test
    @DisplayName("Should throw an exception when person with CPF already exists")
    void createPersonUseCaseErrorCase2() {
        PersonEntity personEntity = new PersonEntity();
        personEntity.setCpf("12345678901");

        when(this.personRepository.findByCpf(personEntity.getCpf())).thenReturn(Optional.of(personEntity));

        assertThrows(PersonFoundException.class, () -> {
            this.createPersonUseCase.execute(personEntity);
        });

        verify(this.personRepository, never()).save(personEntity);
    }
}
