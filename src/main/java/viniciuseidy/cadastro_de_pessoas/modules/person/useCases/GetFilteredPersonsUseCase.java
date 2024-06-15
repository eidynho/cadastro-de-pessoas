package viniciuseidy.cadastro_de_pessoas.modules.person.useCases;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import viniciuseidy.cadastro_de_pessoas.modules.person.entities.PersonEntity;
import viniciuseidy.cadastro_de_pessoas.modules.person.repositories.PersonRepository;

@Service
public class GetFilteredPersonsUseCase {

    @Autowired
    private PersonRepository personRepository;

    public Page<PersonEntity> execute(Optional<String> name, Optional<String> cpf, Optional<LocalDate> birthDate, int page, int perPage) {
        Pageable pageable = PageRequest.of(page, perPage);

        return this.personRepository.findByFilters(name, cpf, birthDate, pageable);
    }
}
