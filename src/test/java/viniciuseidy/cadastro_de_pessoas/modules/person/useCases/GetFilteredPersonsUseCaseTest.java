package viniciuseidy.cadastro_de_pessoas.modules.person.useCases;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import viniciuseidy.cadastro_de_pessoas.modules.person.entities.PersonEntity;
import viniciuseidy.cadastro_de_pessoas.modules.person.repositories.PersonRepository;

public class GetFilteredPersonsUseCaseTest {

    @Mock
    private PersonRepository personRepository;

    @Autowired
    @InjectMocks
    private GetFilteredPersonsUseCase getFilteredPersonsUseCase;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should get filtered persons when everything is ok")
    void getFilteredPersonsUseCaseSuccess() {
        String name = "Vinicius";

        PersonEntity firstPerson = new PersonEntity();
        firstPerson.setName(name);

        List<PersonEntity> personList = Arrays.asList(firstPerson);
        Page<PersonEntity> personPage = new PageImpl<>(personList);

        when(personRepository.findByFilters(
                Optional.of("vini"), 
                Optional.empty(), 
                Optional.empty(), 
                PageRequest.of(0, 10)
            )
        ).thenReturn(personPage);

        Page<PersonEntity> result = this.getFilteredPersonsUseCase.execute(
            Optional.of("vini"), 
            Optional.empty(), 
            Optional.empty(), 
            0, 
            10
        );

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals(name, result.getContent().get(0).getName());
    }
}
