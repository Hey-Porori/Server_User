package porori.backend.user.global.exception;

import static porori.backend.user.global.exception.constant.UserExceptionList.NOT_FOUND_APPLEID_ERROR;

public class NotFoundAppleIdException extends UserException {
    public NotFoundAppleIdException() {
        super(NOT_FOUND_APPLEID_ERROR.getErrorCode(),
                NOT_FOUND_APPLEID_ERROR.getHttpStatus(),
                NOT_FOUND_APPLEID_ERROR.getMessage());
    }
}