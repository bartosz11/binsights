package one.bartosz.metrics.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class SchemaVersionPresentException extends APIException {
    public SchemaVersionPresentException(String message) {
        super(message);
    }
}
