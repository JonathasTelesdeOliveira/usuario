package JonathasTelesdeOliveira.usuario.controller;

import JonathasTelesdeOliveira.usuario.ifraestruture.exceptions.ConflictException;
import JonathasTelesdeOliveira.usuario.ifraestruture.exceptions.ResourceNotFoundException;
import JonathasTelesdeOliveira.usuario.ifraestruture.exceptions.UnauthorizedException;
import JonathasTelesdeOliveira.usuario.ifraestruture.exceptions.dto.ErrorResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobolExeptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleResourceNotFoundException(ResourceNotFoundException ex,
                                                                            HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(buildError(
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage(),
                request.getRequestURI(),
                "Resource Not Found")
        );
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ErrorResponseDTO> handleConflictException(ConflictException ex,
                                                          HttpServletRequest request){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(buildError(
                HttpStatus.CONFLICT.value(),
                ex.getMessage(),
                request.getRequestURI(),
                "Conflict Not found")
        );
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<String> handleUnauthorizedException(UnauthorizedException ex){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    private ErrorResponseDTO buildError(int Status, String mensage, String path, String error) {
        return ErrorResponseDTO.builder()
                .timestamp(LocalDateTime.now())
                .status(Status)
                .message(mensage)
                .error(error)
                .path(path)
                .build();
    }
    
}
