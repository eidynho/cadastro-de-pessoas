package viniciuseidy.cadastro_de_pessoas.modules.contact.useCases;

import java.util.UUID;

import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import viniciuseidy.cadastro_de_pessoas.exceptions.InvalidEmailException;
import viniciuseidy.cadastro_de_pessoas.exceptions.PersonNotFoundException;
import viniciuseidy.cadastro_de_pessoas.modules.contact.entities.ContactEntity;
import viniciuseidy.cadastro_de_pessoas.modules.contact.repositories.ContactRepository;
import viniciuseidy.cadastro_de_pessoas.modules.person.entities.PersonEntity;
import viniciuseidy.cadastro_de_pessoas.modules.person.repositories.PersonRepository;

@Service
public class CreateContactUseCase {
    
    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private PersonRepository personRepository;

    public ContactEntity execute(UUID personId, ContactEntity contact) throws PersonNotFoundException, InvalidEmailException {
        PersonEntity person = this.personRepository.findById(personId)
            .orElseThrow(() -> new PersonNotFoundException());

        var isContactEmailValid = EmailValidator.getInstance().isValid(contact.getEmail());
        if (!isContactEmailValid) {
            throw new InvalidEmailException();
        }

        contact.setPerson(person);

        return this.contactRepository.save(contact);
    }
}
