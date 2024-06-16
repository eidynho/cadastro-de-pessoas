package viniciuseidy.cadastro_de_pessoas.modules.contact.dto;

import java.util.UUID;

public record CreateContactRequestDTO(UUID personId, String name, String phone, String email) {
}
