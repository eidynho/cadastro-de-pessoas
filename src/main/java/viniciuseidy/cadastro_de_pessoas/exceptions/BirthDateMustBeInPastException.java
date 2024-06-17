package viniciuseidy.cadastro_de_pessoas.exceptions;

public class BirthDateMustBeInPastException extends Exception {
    public BirthDateMustBeInPastException() {
        super("A data de aniversário deve estar no passado.");
    }
}
