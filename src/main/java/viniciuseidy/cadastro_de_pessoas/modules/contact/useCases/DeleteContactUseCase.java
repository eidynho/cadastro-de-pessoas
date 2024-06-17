package viniciuseidy.cadastro_de_pessoas.modules.contact.useCases;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import viniciuseidy.cadastro_de_pessoas.exceptions.ContactNotFoundException;
import viniciuseidy.cadastro_de_pessoas.exceptions.OneContactPerPersonIsRequiredException;
import viniciuseidy.cadastro_de_pessoas.modules.contact.entities.ContactEntity;
import viniciuseidy.cadastro_de_pessoas.modules.contact.repositories.ContactRepository;

@Service
public class DeleteContactUseCase {

    @Autowired
    private ContactRepository contactRepository;

    public void execute(UUID contactId) throws ContactNotFoundException, OneContactPerPersonIsRequiredException {
        ContactEntity existentContact = this.contactRepository.findById(contactId)
            .orElseThrow(() -> new ContactNotFoundException());

        int contactsSize = existentContact.getPerson().getContacts().size();
        if (contactsSize == 1) {
            throw new OneContactPerPersonIsRequiredException();
        }

        existentContact.getPerson().getContacts().remove(existentContact);

        this.contactRepository.deleteById(contactId);
    }
}
