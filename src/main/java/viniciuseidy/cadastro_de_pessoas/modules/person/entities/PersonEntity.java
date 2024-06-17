package viniciuseidy.cadastro_de_pessoas.modules.person.entities;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import viniciuseidy.cadastro_de_pessoas.modules.contact.entities.ContactEntity;
import viniciuseidy.cadastro_de_pessoas.modules.person.dto.CreatePersonRequestDTO;
import viniciuseidy.cadastro_de_pessoas.modules.person.dto.UpdatePersonRequestDTO;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "person")
@Table(name = "person")
@Getter
@Setter
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
    @Pattern(regexp = "\\d{11}", message = "O CPF deve possuir 11 caracteres e apenas dígitos.")
    private String cpf;

    @Past(message = "A data de aniversário deve estar no passado.")
    @Column(nullable = false)
    private LocalDate birthDate;

    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL, orphanRemoval = true)
    @Size(min = 1, message = "A pessoa deve possuir ao menos um contato.")
    @JsonManagedReference
    private List<ContactEntity> contacts;

    public PersonEntity(CreatePersonRequestDTO data) {
        this.name = data.name();
        this.cpf = data.cpf();
        this.birthDate = data.birthDate();
    }

     public PersonEntity(UpdatePersonRequestDTO data) {
        this.id = data.id();
        this.name = data.name();
        this.cpf = data.cpf();
        this.birthDate = data.birthDate();
    }
}
