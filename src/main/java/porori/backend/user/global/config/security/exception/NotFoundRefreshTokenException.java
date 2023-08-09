package porori.backend.user.global.config.security.exception;

import static porori.backend.user.global.config.security.constant.SecurityExceptionList.NOT_FOUND_REFRESH_TOKEN_ERROR;
import static porori.backend.user.global.config.security.constant.SecurityExceptionList.REQUIRED_LOGGED_IN;

public class NotFoundRefreshTokenException extends SecurityException{

    public NotFoundRefreshTokenException() {
        super(NOT_FOUND_REFRESH_TOKEN_ERROR.getErrorCode(),
                NOT_FOUND_REFRESH_TOKEN_ERROR.getHttpStatus(),
                NOT_FOUND_REFRESH_TOKEN_ERROR.getMessage()
        );
    }
}
