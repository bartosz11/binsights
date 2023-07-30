package one.bartosz.metrics.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidDateException extends APIException {
    public InvalidDateException(String message) {
        super(message);
    }
}
