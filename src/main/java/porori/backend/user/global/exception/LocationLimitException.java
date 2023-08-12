package porori.backend.user.global.exception;

import static porori.backend.user.global.exception.constant.UserExceptionList.LOCATION_LIMIT_ERROR;

public class LocationLimitException extends UserException {
    public LocationLimitException() {
        super(LOCATION_LIMIT_ERROR.getErrorCode(),
                LOCATION_LIMIT_ERROR.getHttpStatus(),
                LOCATION_LIMIT_ERROR.getMessage()
        );
    }
}
