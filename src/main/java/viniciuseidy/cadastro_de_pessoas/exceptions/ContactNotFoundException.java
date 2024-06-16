package viniciuseidy.cadastro_de_pessoas.exceptions;

public class ContactNotFoundException extends RuntimeException {
    public ContactNotFoundException() {
        super("Contact not found.");
    }
}
