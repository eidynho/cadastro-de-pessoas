package viniciuseidy.cadastro_de_pessoas.modules.person.useCases;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import viniciuseidy.cadastro_de_pessoas.exceptions.CPFAlreadyExistsException;
import viniciuseidy.cadastro_de_pessoas.exceptions.PersonNotFoundException;
import viniciuseidy.cadastro_de_pessoas.modules.person.entities.PersonEntity;
import viniciuseidy.cadastro_de_pessoas.modules.person.repositories.PersonRepository;

public class UpdatePersonUseCaseTest {
    
    @Mock
    private PersonRepository personRepository;

    @Autowired
    @InjectMocks
    private UpdatePersonUseCase updatePersonUseCase;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should update a person when everything is ok")
    void updatePersonUseCaseSuccess() {
        UUID id = UUID.randomUUID();
        String newCpf = "98765432100";

        PersonEntity existingPersonEntity = new PersonEntity();
        existingPersonEntity.setId(id);

        when(this.personRepository.findById(id)).thenReturn(Optional.of(existingPersonEntity));
        when(this.personRepository.findByCpf(newCpf)).thenReturn(Optional.empty());

        PersonEntity updatedPersonEntity = new PersonEntity();
        updatedPersonEntity.setId(id);
        updatedPersonEntity.setName("Eidy");
        updatedPersonEntity.setCpf(newCpf);
        updatedPersonEntity.setBirthDate(LocalDate.of(2001, 9, 2));

        when(this.personRepository.save(any(PersonEntity.class))).thenReturn(updatedPersonEntity);

        PersonEntity result = this.updatePersonUseCase.execute(updatedPersonEntity);

        assertEquals(result.getName(), updatedPersonEntity.getName());
        assertEquals(result.getCpf(), updatedPersonEntity.getCpf());
        assertEquals(result.getBirthDate(), updatedPersonEntity.getBirthDate());
        assertNotNull(result);
    }

    @Test
    @DisplayName("Should return PersonNotFoundException when person does not exists")
    void updatePersonUseCaseError1() {
        UUID id = UUID.randomUUID();

        PersonEntity personEntity = new PersonEntity();
        personEntity.setId(id);

        when(this.personRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(PersonNotFoundException.class, () -> {
            this.updatePersonUseCase.execute(personEntity);
        });
    }

    @Test
    @DisplayName("Should return IllegalArgumentException when CPF is invalid")
    void updatePersonUseCaseError2() {
        UUID id = UUID.randomUUID();
        String cpf = "12345678900";

        PersonEntity personEntity = new PersonEntity();
        personEntity.setId(id);
        personEntity.setCpf(cpf);

        PersonEntity personEntityToUpdate = new PersonEntity();
        personEntityToUpdate.setId(id);
        personEntityToUpdate.setCpf("invalid-cpf");

        when(this.personRepository.findById(id)).thenReturn(Optional.of(personEntity));

        assertThrows(IllegalArgumentException.class, () -> {
            this.updatePersonUseCase.execute(personEntityToUpdate);
        });
    }

    @Test
    @DisplayName("Should return CPFAlreadyExistsException when CPF is valid but already exists")
    void updatePersonUseCaseError3() {
        String cpfToBeTested = "12345678900";

        PersonEntity randomPersonEntity = new PersonEntity();
        randomPersonEntity.setCpf(cpfToBeTested);

        UUID id = UUID.randomUUID();

        PersonEntity personEntityToUpdate = new PersonEntity();
        personEntityToUpdate.setId(id);

        PersonEntity updatedPersonEntity = new PersonEntity();
        updatedPersonEntity.setId(id);
        updatedPersonEntity.setCpf(cpfToBeTested);

        when(this.personRepository.findById(id)).thenReturn(Optional.of(personEntityToUpdate));
        when(this.personRepository.findByCpf(cpfToBeTested)).thenReturn(Optional.of(randomPersonEntity));

        assertThrows(CPFAlreadyExistsException.class, () -> {
            this.updatePersonUseCase.execute(updatedPersonEntity);
        });
    }
}
