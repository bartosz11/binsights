package one.bartosz.metrics.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class InvalidInviteCodeException extends APIException {
    public InvalidInviteCodeException(String message) {
        super(message);
    }
}
