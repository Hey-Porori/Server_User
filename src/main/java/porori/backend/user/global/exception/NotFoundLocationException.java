package porori.backend.user.global.exception;

import porori.backend.user.global.exception.constant.UserExceptionList;

import static porori.backend.user.global.exception.constant.UserExceptionList.NOT_FOUND_LOCATION_ERROR;

public class NotFoundLocationException extends UserException{
    public NotFoundLocationException(){
        super(NOT_FOUND_LOCATION_ERROR.getErrorCode(),
                NOT_FOUND_LOCATION_ERROR.getHttpStatus(),
                NOT_FOUND_LOCATION_ERROR.getMessage());
    }
}
