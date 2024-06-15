package viniciuseidy.cadastro_de_pessoas.modules.person.useCases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import viniciuseidy.cadastro_de_pessoas.exceptions.PersonFoundException;
import viniciuseidy.cadastro_de_pessoas.modules.person.entities.PersonEntity;
import viniciuseidy.cadastro_de_pessoas.modules.person.repositories.PersonRepository;

@Service
public class CreatePersonUseCase {

    @Autowired
    private PersonRepository personRepository;

    public PersonEntity execute(PersonEntity personEntity) {
        if (personEntity.getCpf() == null || personEntity.getCpf().length() != 11) {
            throw new IllegalArgumentException("CPF must be exactly 11 digits");
        }
        
        this.personRepository
            .findByCpf(personEntity.getCpf())
            .ifPresent((person) -> {
                throw new PersonFoundException();
            });

        return this.personRepository.save(personEntity);
    }
}
