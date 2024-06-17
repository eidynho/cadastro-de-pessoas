package viniciuseidy.cadastro_de_pessoas.exceptions;

public class CPFAlreadyExistsException extends Exception {
    public CPFAlreadyExistsException() {
        super("CPF já cadastrado.");
    }
}
