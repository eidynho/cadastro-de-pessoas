package viniciuseidy.cadastro_de_pessoas.modules.person.useCases;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import viniciuseidy.cadastro_de_pessoas.exceptions.PersonNotFoundException;
import viniciuseidy.cadastro_de_pessoas.modules.person.repositories.PersonRepository;

@Service
public class DeletePersonUseCase {

    @Autowired
    private PersonRepository personRepository;

    public void execute(UUID id) {
        this.personRepository
            .findById(id)
            .orElseThrow(() -> new PersonNotFoundException());

        this.personRepository.deleteById(id);
    }
}
