package JonathasTelesdeOliveira.usuario.ifraestruture.exceptions;

public class RequisicaoNaoEsperada extends RuntimeException {
    public RequisicaoNaoEsperada(String message) {
        super(message);
    }
    public RequisicaoNaoEsperada(String mensagem, Throwable throwable) {
        super(mensagem, throwable);
    }
}
