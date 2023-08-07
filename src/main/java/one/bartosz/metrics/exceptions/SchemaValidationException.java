package one.bartosz.metrics.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class SchemaValidationException extends APIException {
    public SchemaValidationException(String message) {
        super(message);
    }
}
