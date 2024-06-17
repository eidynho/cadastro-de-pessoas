package viniciuseidy.cadastro_de_pessoas.exceptions;

public class PersonNotFoundException extends Exception {
    public PersonNotFoundException() {
        super("Pessoa não encontrada.");
    }
}
