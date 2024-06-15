package viniciuseidy.cadastro_de_pessoas.modules.person.repositories;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    @Test
    @DisplayName("Should get a page with passing all filters of person")
    void findByFiltersCase1() {
        String name = "Vinicius";
        String cpf = "12345678900";
        LocalDate birthDate = this.transformBirthDateStrToLocalDate("2001-09-02");

        CreatePersonRequestDTO data = new CreatePersonRequestDTO(name, cpf, birthDate);

        this.createPerson(data);

        Pageable pageable = PageRequest.of(0, 10);

        Page<PersonEntity> result = this.personRepository.findByFilters(
            Optional.of(name), 
            Optional.of(cpf), 
            Optional.of(birthDate), 
            pageable
        );
        
        assertThat(result).isNotNull();
        assertThat(result).isNotEmpty();
        assertThat(result.getTotalElements()).isEqualTo(1);

        PersonEntity person = result.getContent().get(0);
        assertThat(person.getName()).isEqualTo(name);
        assertThat(person.getCpf()).isEqualTo(cpf);
        assertThat(person.getBirthDate()).isEqualTo(birthDate);
    }

    @Test
    @DisplayName("Should get a page with just birthDate filter of person")
    void findByFiltersCase2() {
        String name = "Vinicius";
        String cpf = "12345678900";
        LocalDate birthDate = this.transformBirthDateStrToLocalDate("2001-09-02");

        CreatePersonRequestDTO data = new CreatePersonRequestDTO(name, cpf, birthDate);

        this.createPerson(data);

        Pageable pageable = PageRequest.of(0, 10);

        Page<PersonEntity> result = this.personRepository.findByFilters(
            Optional.empty(), 
            Optional.empty(), 
            Optional.of(birthDate), 
            pageable
        );

        PersonEntity person = result.getContent().get(0);
        assertThat(person.getName()).isEqualTo(name);
        assertThat(person.getCpf()).isEqualTo(cpf);
        assertThat(person.getBirthDate()).isEqualTo(birthDate);
    }

    @Test
    @DisplayName("Should get persons successfully with pagination")
    void findByFiltersCase3() {
        LocalDate birthDate = this.transformBirthDateStrToLocalDate("2001-09-02");

        CreatePersonRequestDTO firstPerson = new CreatePersonRequestDTO("Vinicius", "12345678900", birthDate);
        this.createPerson(firstPerson);

        CreatePersonRequestDTO secondPerson = new CreatePersonRequestDTO("Eidy", "12345678901", birthDate);
        this.createPerson(secondPerson);

        String thirdPersonName = "Okuda";
        String thirdPersonCPF = "12345678902";
        CreatePersonRequestDTO thirdPerson = new CreatePersonRequestDTO(thirdPersonName, thirdPersonCPF, birthDate);
        this.createPerson(thirdPerson);

        Pageable pageable = PageRequest.of(1, 2);

        Page<PersonEntity> result = this.personRepository.findByFilters(
            Optional.empty(), 
            Optional.empty(), 
            Optional.of(birthDate), 
            pageable
        );

        assertThat(result).isNotNull();
        assertThat(result).isNotEmpty();
        assertThat(result.getTotalElements()).isEqualTo(3);

        PersonEntity firstPersonOfSecondPage = result.getContent().get(0);
        assertThat(firstPersonOfSecondPage.getName()).isEqualTo(thirdPersonName);
        assertThat(firstPersonOfSecondPage.getCpf()).isEqualTo(thirdPersonCPF);
        assertThat(firstPersonOfSecondPage.getBirthDate()).isEqualTo(birthDate);
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
