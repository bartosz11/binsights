package one.bartosz.metrics.exceptions;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ResponseStatus;

public class UserNotFoundException extends UsernameNotFoundException {
    private final String message;
    public UserNotFoundException(String msg) {
        super(msg);
        this.message = msg;
    }

    //had to copy these because i can't really extend APIException if i want to throw my own exception
    public String getError() {
        return this.getClass().getSimpleName();
    }

    public int getCode() {
        return this.getClass().getAnnotation(ResponseStatus.class).value().value();
    }

    public String getMessage() {
        return message;
    }
}
