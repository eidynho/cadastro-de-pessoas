package viniciuseidy.cadastro_de_pessoas.exceptions;

public class InvalidEmailException extends Exception {
    public InvalidEmailException() {
        super("Invalid email format.");
    }
}
