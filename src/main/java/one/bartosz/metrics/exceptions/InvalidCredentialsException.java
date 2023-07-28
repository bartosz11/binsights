package one.bartosz.metrics.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class InvalidCredentialsException extends APIException {
    public InvalidCredentialsException(String message) {
        super(message);
    }
}
