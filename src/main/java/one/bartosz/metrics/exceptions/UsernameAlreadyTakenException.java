package one.bartosz.metrics.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class UsernameAlreadyTakenException extends APIException {
    public UsernameAlreadyTakenException(String message) {
        super(message);
    }
}
