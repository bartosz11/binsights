package one.bartosz.metrics.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class SchemaDisabledException extends APIException{
    public SchemaDisabledException(String message) {
        super(message);
    }
}
