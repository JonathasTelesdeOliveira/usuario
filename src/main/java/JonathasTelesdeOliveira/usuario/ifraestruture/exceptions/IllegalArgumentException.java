package JonathasTelesdeOliveira.usuario.ifraestruture.exceptions;

import org.springframework.security.core.AuthenticationException;

public class IllegalArgumentException extends AuthenticationException {
    public IllegalArgumentException(String message) {
        super(message);
    }
    public IllegalArgumentException(String mensagem, Throwable throwable){
        super(mensagem, throwable);
    }
}
