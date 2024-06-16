package viniciuseidy.cadastro_de_pessoas.modules.person.dto;

import java.time.LocalDate;

public record CreatePersonRequestDTO(
    String name,
    String cpf,
    LocalDate birthDate,
    String contactName,
    String contactPhone,
    String contactEmail
) {
}
