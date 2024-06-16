package viniciuseidy.cadastro_de_pessoas.modules.contact.entities;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import viniciuseidy.cadastro_de_pessoas.modules.contact.dto.CreateContactRequestDTO;
import viniciuseidy.cadastro_de_pessoas.modules.contact.dto.UpdateContactRequestDTO;
import viniciuseidy.cadastro_de_pessoas.modules.person.entities.PersonEntity;

@Entity(name = "contact")
@Table(name = "contact")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class ContactEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String phone;

    @Email(message = "Email should be valid")
    @Column(nullable = false)
    private String email;

    @ManyToOne
    @JoinColumn(name = "person_id", nullable = false)
    @JsonBackReference
    private PersonEntity person;

    public ContactEntity(CreateContactRequestDTO data) {
        this.name = data.name();
        this.phone = data.phone();
        this.email = data.email();
    }

    public ContactEntity(UpdateContactRequestDTO data) {
        this.id = data.contactId();
        this.name = data.name();
        this.phone = data.phone();
        this.email = data.email();
    }
}