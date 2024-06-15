package viniciuseidy.cadastro_de_pessoas.exceptions;

public class CPFAlreadyExistsException extends RuntimeException {
    public CPFAlreadyExistsException() {
        super("CPF already exists.");
    }
}
