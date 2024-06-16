package viniciuseidy.cadastro_de_pessoas.modules.person.useCases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import viniciuseidy.cadastro_de_pessoas.exceptions.PersonFoundException;
import viniciuseidy.cadastro_de_pessoas.modules.person.entities.ContactEntity;
import viniciuseidy.cadastro_de_pessoas.modules.person.entities.PersonEntity;
import viniciuseidy.cadastro_de_pessoas.modules.person.repositories.ContactRepository;
import viniciuseidy.cadastro_de_pessoas.modules.person.repositories.PersonRepository;

@Service
public class CreatePersonUseCase {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private ContactRepository contactRepository;

    @Transactional
    public PersonEntity execute(PersonEntity person, ContactEntity contact) {
        if (!person.getCpf().matches("\\d{11}")) {
            throw new IllegalArgumentException("CPF must have exactly 11 digits and contain only digits");
        }
        
        this.personRepository
            .findByCpf(person.getCpf())
            .ifPresent((personEntity) -> {
                throw new PersonFoundException();
            });
      
        PersonEntity createdPerson = this.personRepository.save(person);

        contact.setPerson(person);
        this.contactRepository.save(contact);

        return createdPerson;
    }
}
