package viniciuseidy.cadastro_de_pessoas.exceptions;

public class PersonNotFoundException extends Exception {
    public PersonNotFoundException() {
        super("Person not found.");
    }
}
