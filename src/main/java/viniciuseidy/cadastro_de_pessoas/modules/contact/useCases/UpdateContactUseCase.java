package viniciuseidy.cadastro_de_pessoas.modules.contact.useCases;

import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import viniciuseidy.cadastro_de_pessoas.exceptions.ContactNotFoundException;
import viniciuseidy.cadastro_de_pessoas.exceptions.InvalidEmailException;
import viniciuseidy.cadastro_de_pessoas.modules.contact.entities.ContactEntity;
import viniciuseidy.cadastro_de_pessoas.modules.contact.repositories.ContactRepository;

@Service
public class UpdateContactUseCase {
    
    @Autowired
    private ContactRepository contactRepository;

    public ContactEntity execute(ContactEntity contact) {
        ContactEntity existentContact = this.contactRepository
            .findById(contact.getId())
            .orElseThrow(() -> {
                throw new ContactNotFoundException();
            });

        contact.setPerson(existentContact.getPerson());

        if (contact.getEmail() != null) {
            var isContactEmailValid = EmailValidator.getInstance().isValid(contact.getEmail());
            if (!isContactEmailValid) {
                throw new InvalidEmailException();
            }
        }

        return this.contactRepository.save(contact);
    }
}
