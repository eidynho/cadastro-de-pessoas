package viniciuseidy.cadastro_de_pessoas.exceptions;

public class PersonFoundException extends RuntimeException {
    public PersonFoundException() {
        super("Person already exists.");
    }
}
