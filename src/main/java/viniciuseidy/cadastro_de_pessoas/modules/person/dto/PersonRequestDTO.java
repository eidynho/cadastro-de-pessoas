package viniciuseidy.cadastro_de_pessoas.modules.person.dto;

import java.time.LocalDate;

public record PersonRequestDTO(String name, String cpf, LocalDate birthDate) {
}
