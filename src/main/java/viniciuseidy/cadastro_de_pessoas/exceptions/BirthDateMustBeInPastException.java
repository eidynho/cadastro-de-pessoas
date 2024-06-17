package viniciuseidy.cadastro_de_pessoas.exceptions;

public class BirthDateMustBeInPastException extends Exception {
    public BirthDateMustBeInPastException() {
        super("Birth date must be in past.");
    }
}
