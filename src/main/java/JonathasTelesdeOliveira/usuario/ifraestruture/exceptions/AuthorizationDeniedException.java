package JonathasTelesdeOliveira.usuario.ifraestruture.exceptions;

import org.springframework.security.core.AuthenticationException;

public class AuthorizationDeniedException extends AuthenticationException {
    /**
     * Constructs a <code>BadCredentialsException</code> with the specified message.
     * @param msg the detail message
     */
    public AuthorizationDeniedException(String msg) {
        super(msg);
    }

    /**
     * Constructs a <code>BadCredentialsException</code> with the specified message and
     * root cause.
     * @param msg the detail message
     * @param cause root cause
     */
    public AuthorizationDeniedException(String msg, Throwable cause) {
        super(msg, cause);
    }

}
