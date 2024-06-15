package viniciuseidy.cadastro_de_pessoas.modules.person.dto;

import java.time.LocalDate;
import java.util.UUID;

public record UpdatePersonRequestDTO(UUID id, String name, String cpf, LocalDate birthDate) {
}
