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

        if (personEntity.getName() != null) {
            existingPersonEntity.setName(personEntity.getName());
        }

        boolean isSameCPF = personEntity.getCpf().equals(existingPersonEntity.getCpf());

        if (personEntity.getCpf() != null && !isSameCPF) {
            this.personRepository
                .findByCpf(personEntity.getCpf())
                .ifPresent((person) -> {
                    throw new CPFAlreadyExistsException();
                });
            
            existingPersonEntity.setCpf(personEntity.getCpf());
        }

        if (personEntity.getBirthDate() != null) {
            existingPersonEntity.setBirthDate(personEntity.getBirthDate());
        }
        
        return this.personRepository.save(personEntity);
    }
}
