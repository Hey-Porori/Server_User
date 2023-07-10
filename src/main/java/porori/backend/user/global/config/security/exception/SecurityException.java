package porori.backend.user.global.config.security.exception;

import org.springframework.http.HttpStatus;
import porori.backend.user.global.exception.ApplicationException;

public abstract class SecurityException  extends ApplicationException {
    protected SecurityException(String errorCode, HttpStatus httpStatus, String message) {
        super(errorCode, httpStatus, message);
    }
}
