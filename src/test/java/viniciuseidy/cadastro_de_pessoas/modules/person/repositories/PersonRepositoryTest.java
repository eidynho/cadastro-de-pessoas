package viniciuseidy.cadastro_de_pessoas.modules.person.repositories;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import static org.assertj.core.api.Assertions.assertThat;

import jakarta.persistence.EntityManager;
import viniciuseidy.cadastro_de_pessoas.modules.person.dto.CreatePersonRequestDTO;
import viniciuseidy.cadastro_de_pessoas.modules.person.entities.PersonEntity;

@DataJpaTest
@ActiveProfiles("test")
public class PersonRepositoryTest {

    @Autowired
    PersonRepository personRepository;

    @Autowired
    EntityManager entityManager;
    
    @Test
    @DisplayName("Should get Person successfully from database")
    void findByCpfSuccess() {
        String cpf = "12345678900";
        LocalDate birthDate = this.transformBirthDateStrToLocalDate("2001-09-02");

        CreatePersonRequestDTO data = new CreatePersonRequestDTO("Vinicius", cpf, birthDate);

        this.createPerson(data);

        Optional<PersonEntity> result = this.personRepository.findByCpf(cpf);

        assertThat(result.isPresent()).isTrue();
    }

    @Test
    @DisplayName("Should not get Person from database when person does not exists.")
    void findByCpfError() {
        String cpf = "12345678900";

        Optional<PersonEntity> result = this.personRepository.findByCpf(cpf);

        assertThat(result.isEmpty()).isTrue();
    }

    private PersonEntity createPerson(CreatePersonRequestDTO data) {
        PersonEntity newPerson = new PersonEntity(data);

        this.entityManager.persist(newPerson);

        return newPerson;
    }

    private LocalDate transformBirthDateStrToLocalDate(String birthDateStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(birthDateStr, formatter);
    }
}
