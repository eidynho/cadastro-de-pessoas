package viniciuseidy.cadastro_de_pessoas.modules.contact.dto;

import java.util.UUID;

public record UpdateContactRequestDTO(UUID contactId, String name, String phone, String email) {
}
