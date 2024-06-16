package viniciuseidy.cadastro_de_pessoas.modules.contact.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import viniciuseidy.cadastro_de_pessoas.modules.contact.entities.ContactEntity;

public interface ContactRepository extends JpaRepository<ContactEntity, UUID> {
}
