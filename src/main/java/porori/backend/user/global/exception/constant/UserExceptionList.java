package porori.backend.user.global.exception.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum UserExceptionList {
    CONNECTION_ERROR("U0001", HttpStatus.UNAUTHORIZED, "AccessToken 값이 잘못되었습니다"),
    NOT_FOUND_APPLEID_ERROR("U0002", HttpStatus.NOT_FOUND, "해당 appleId로 User를 찾을 수 없습니다."),
    NOT_FOUND_USER_ERROR("U0003", HttpStatus.NOT_FOUND, "해당 id로 User를 찾을 수 없습니다."),
    LOCATION_LIMIT_ERROR("U0004",HttpStatus.BAD_REQUEST, "더 이상 위치를 추가할 수 없습니다."),
    NOT_FOUND_LOCATION_ERROR("U0005",HttpStatus.NOT_FOUND,"유효하지 않는 장소입니다.");
    private final String errorCode;
    private final HttpStatus httpStatus;
    private final String message;
}
