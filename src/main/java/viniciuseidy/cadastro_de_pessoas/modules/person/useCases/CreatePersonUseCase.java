package viniciuseidy.cadastro_de_pessoas.modules.person.useCases;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import viniciuseidy.cadastro_de_pessoas.exceptions.BirthDateMustBeInPastException;
import viniciuseidy.cadastro_de_pessoas.exceptions.CPFAlreadyExistsException;
import viniciuseidy.cadastro_de_pessoas.modules.contact.entities.ContactEntity;
import viniciuseidy.cadastro_de_pessoas.modules.contact.repositories.ContactRepository;
import viniciuseidy.cadastro_de_pessoas.modules.person.entities.PersonEntity;
import viniciuseidy.cadastro_de_pessoas.modules.person.repositories.PersonRepository;

@Service
public class CreatePersonUseCase {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private ContactRepository contactRepository;

    @Transactional
    public PersonEntity execute(PersonEntity person, ContactEntity contact) throws IllegalArgumentException, CPFAlreadyExistsException, BirthDateMustBeInPastException {
        if (!person.getCpf().matches("\\d{11}")) {
            throw new IllegalArgumentException("O CPF deve possuir 11 caracteres e apenas dígitos.");
        }
        
        Optional<PersonEntity> personWithCPF = this.personRepository.findByCpf(person.getCpf());
        if (personWithCPF.isPresent()) {
            throw new CPFAlreadyExistsException();
        }

        if (person.getBirthDate() != null) {
            boolean isBirthDateInPast = person.getBirthDate().isBefore(LocalDate.now());
            if (!isBirthDateInPast) {
                throw new BirthDateMustBeInPastException();
            }
        }
      
        PersonEntity createdPerson = this.personRepository.save(person);

        contact.setPerson(person);
        this.contactRepository.save(contact);

        return createdPerson;
    }
}
