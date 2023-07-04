package porori.backend.user.global.config.security.exception;

import static porori.backend.user.global.config.security.constant.SecurityExceptionList.REQUIRED_LOGGED_IN;

public class RequiredLoggedInException extends SecurityException{
    public RequiredLoggedInException() {
        super(REQUIRED_LOGGED_IN.getErrorCode(),
                REQUIRED_LOGGED_IN.getHttpStatus(),
                REQUIRED_LOGGED_IN.getMessage()
        );
    }
}
