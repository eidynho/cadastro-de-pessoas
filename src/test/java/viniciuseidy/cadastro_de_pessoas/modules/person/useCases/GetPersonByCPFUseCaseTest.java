package viniciuseidy.cadastro_de_pessoas.modules.person.useCases;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import viniciuseidy.cadastro_de_pessoas.exceptions.PersonNotFoundException;
import viniciuseidy.cadastro_de_pessoas.modules.person.entities.PersonEntity;
import viniciuseidy.cadastro_de_pessoas.modules.person.repositories.PersonRepository;

public class GetPersonByCPFUseCaseTest {

    @Mock
    private PersonRepository personRepository;

    @Autowired
    @InjectMocks
    private GetPersonByCPFUseCase getPersonByCPFUseCase;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should get a person by cpf when everything is ok")
    void getPersonByCPFUseCaseSuccess() throws PersonNotFoundException {
        String cpf = "12345678900";
        PersonEntity personEntity = new PersonEntity();
        personEntity.setCpf(cpf);

        when(this.personRepository.findByCpf(cpf)).thenReturn(Optional.of(personEntity));

        PersonEntity result = this.getPersonByCPFUseCase.execute(cpf);

        verify(this.personRepository).findByCpf(cpf);
        assertNotNull(result);
        assertEquals(cpf, result.getCpf());
    }

    @Test
    @DisplayName("Should throw PersonNotFoundException when CPF does not exist")
    void getPersonByCPFUseCaseError() {
        String cpf = "12345678900";

        when(this.personRepository.findByCpf(cpf)).thenReturn(Optional.empty());

        assertThrows(PersonNotFoundException.class, () -> {
            this.getPersonByCPFUseCase.execute(cpf);
        });
    }
}
