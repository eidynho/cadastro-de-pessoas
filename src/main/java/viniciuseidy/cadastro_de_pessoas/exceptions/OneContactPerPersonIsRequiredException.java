package viniciuseidy.cadastro_de_pessoas.exceptions;

public class OneContactPerPersonIsRequiredException extends Exception {
    public OneContactPerPersonIsRequiredException() {
        super("A pessoa precisa ter pelo menos um contato.");
    }
}
