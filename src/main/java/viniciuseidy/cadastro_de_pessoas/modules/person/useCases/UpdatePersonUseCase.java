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

    public PersonEntity execute(PersonEntity updatedPerson) {  
        PersonEntity existingPersonEntity = this.personRepository
            .findById(updatedPerson.getId())
            .orElseThrow(() -> new PersonNotFoundException());

        if (updatedPerson.getName() != null) {
            existingPersonEntity.setName(updatedPerson.getName());
        }

        boolean isSameCPF = updatedPerson.getCpf().equals(existingPersonEntity.getCpf());

        if (updatedPerson.getCpf() != null && !isSameCPF) {
            if (!updatedPerson.getCpf().matches("\\d{11}")) {
                throw new IllegalArgumentException("CPF must have exactly 11 digits and contain only digits");
            }

            this.personRepository
                .findByCpf(updatedPerson.getCpf())
                .ifPresent((person) -> {
                    throw new CPFAlreadyExistsException();
                });

            existingPersonEntity.setCpf(updatedPerson.getCpf());
        }

        if (updatedPerson.getBirthDate() != null) {
            existingPersonEntity.setBirthDate(updatedPerson.getBirthDate());
        }

        return this.personRepository.save(existingPersonEntity);
    }
}
