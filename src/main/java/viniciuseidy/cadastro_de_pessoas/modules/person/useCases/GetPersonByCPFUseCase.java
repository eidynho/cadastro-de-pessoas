package viniciuseidy.cadastro_de_pessoas.modules.person.useCases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import viniciuseidy.cadastro_de_pessoas.exceptions.PersonNotFoundException;
import viniciuseidy.cadastro_de_pessoas.modules.person.entities.PersonEntity;
import viniciuseidy.cadastro_de_pessoas.modules.person.repositories.PersonRepository;

@Service
public class GetPersonByCPFUseCase {

    @Autowired
    private PersonRepository personRepository;

    public PersonEntity execute(String cpf) throws PersonNotFoundException {
         return this.personRepository.findByCpf(cpf)
            .orElseThrow(() -> new PersonNotFoundException());
    }
}
