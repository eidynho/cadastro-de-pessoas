package viniciuseidy.cadastro_de_pessoas.exceptions;

public class PersonNotFoundException extends RuntimeException {
    public PersonNotFoundException() {
        super("Person not found.");
    }
}
