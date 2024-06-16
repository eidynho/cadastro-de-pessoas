package viniciuseidy.cadastro_de_pessoas.modules.person.useCases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import viniciuseidy.cadastro_de_pessoas.exceptions.CPFAlreadyExistsException;
import viniciuseidy.cadastro_de_pessoas.exceptions.PersonNotFoundException;
import viniciuseidy.cadastro_de_pessoas.modules.person.entities.PersonEntity;
import viniciuseidy.cadastro_de_pessoas.modules.person.repositories.PersonRepository;

@Service
public class UpdatePersonUseCase {
    @Autowired
    private PersonRepository personRepository;

    public PersonEntity execute(PersonEntity personEntity) {  
        PersonEntity existingPersonEntity = this.personRepository
            .findById(personEntity.getId())
            .orElseThrow(() -> new PersonNotFoundException());

        boolean isSameCPF = personEntity.getCpf().equals(existingPersonEntity.getCpf());

        if (personEntity.getCpf() != null && !isSameCPF) {
            if (!personEntity.getCpf().matches("\\d{11}")) {
                throw new IllegalArgumentException("CPF must have exactly 11 digits and contain only digits");
            }

            this.personRepository
                .findByCpf(personEntity.getCpf())
                .ifPresent((person) -> {
                    throw new CPFAlreadyExistsException();
                });
        }
        
        return this.personRepository.save(personEntity);
    }
}
