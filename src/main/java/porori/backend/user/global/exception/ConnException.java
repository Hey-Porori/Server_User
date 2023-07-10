package porori.backend.user.global.exception;

import static porori.backend.user.global.exception.constant.UserExceptionList.CONNECTION_ERROR;

public class ConnException extends UserException{

    public ConnException(){
        super(CONNECTION_ERROR.getErrorCode(),
                CONNECTION_ERROR.getHttpStatus(),
                CONNECTION_ERROR.getMessage());
    }
}