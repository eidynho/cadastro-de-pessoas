package viniciuseidy.cadastro_de_pessoas.modules.contact.useCases;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import viniciuseidy.cadastro_de_pessoas.exceptions.ContactNotFoundException;
import viniciuseidy.cadastro_de_pessoas.modules.contact.repositories.ContactRepository;

@Service
public class DeleteContactUseCase {
    
    @Autowired
    private ContactRepository contactRepository;

    public void execute(UUID contactId) throws ContactNotFoundException {
        this.contactRepository.findById(contactId)
            .orElseThrow(() -> new ContactNotFoundException());

        this.contactRepository.deleteById(contactId);
    }
}
