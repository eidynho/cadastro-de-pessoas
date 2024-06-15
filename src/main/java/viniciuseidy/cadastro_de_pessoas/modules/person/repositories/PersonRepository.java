package viniciuseidy.cadastro_de_pessoas.modules.person.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import viniciuseidy.cadastro_de_pessoas.modules.person.entities.PersonEntity;
import java.util.Optional;

public interface PersonRepository extends JpaRepository<PersonEntity, UUID> {
    Optional<PersonEntity> findByCpf(String cpf);
}
