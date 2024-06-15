package viniciuseidy.cadastro_de_pessoas.modules.person.entities;

import java.time.LocalDate;
import java.util.UUID;

import viniciuseidy.cadastro_de_pessoas.modules.person.dto.PersonRequestDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "person")
@Table(name = "person")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class PersonEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String cpf;

    @Past(message = "Birth date must be in the past")
    @Column(nullable = false)
    private LocalDate birthDate;

    public PersonEntity(PersonRequestDTO data) {
        this.name = data.name();
        this.cpf = data.cpf();
        this.birthDate = data.birthDate();
    }
}
