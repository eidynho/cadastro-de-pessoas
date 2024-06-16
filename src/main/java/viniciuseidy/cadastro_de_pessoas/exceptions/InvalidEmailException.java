package viniciuseidy.cadastro_de_pessoas.exceptions;

public class InvalidEmailException extends RuntimeException {
    public InvalidEmailException() {
        super("Invalid email format.");
    }
}
