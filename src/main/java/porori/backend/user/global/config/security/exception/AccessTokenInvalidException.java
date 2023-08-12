package porori.backend.user.global.config.security.exception;

import static porori.backend.user.global.config.security.constant.SecurityExceptionList.ACCESS_TOKEN_INVALID_ERROR;

public class AccessTokenInvalidException extends SecurityException{
    public AccessTokenInvalidException(){
        super(ACCESS_TOKEN_INVALID_ERROR.getErrorCode(),
                ACCESS_TOKEN_INVALID_ERROR.getHttpStatus(),
                ACCESS_TOKEN_INVALID_ERROR.getMessage());
    }
}
