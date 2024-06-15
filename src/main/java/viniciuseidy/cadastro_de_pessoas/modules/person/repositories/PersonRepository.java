package viniciuseidy.cadastro_de_pessoas.modules.person.repositories;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import viniciuseidy.cadastro_de_pessoas.modules.person.entities.PersonEntity;

import java.time.LocalDate;
import java.util.Optional;

public interface PersonRepository extends JpaRepository<PersonEntity, UUID> {
    Optional<PersonEntity> findByCpf(String cpf);

    @Query("SELECT p FROM person p WHERE " +
        "(:name IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%'))) AND " +
        "(:cpf IS NULL OR p.cpf = :cpf) AND " +
        "(:birthDate IS NULL OR p.birthDate = :birthDate)")
    Page<PersonEntity> findByFilters(
        Optional<String> name,
        Optional<String> cpf,
        Optional<LocalDate> birthDate,
        Pageable pageable);
}
