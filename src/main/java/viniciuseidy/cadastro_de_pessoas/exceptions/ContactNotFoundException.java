package viniciuseidy.cadastro_de_pessoas.exceptions;

public class ContactNotFoundException extends Exception {
    public ContactNotFoundException() {
        super("Contato não encontrado.");
    }
}
