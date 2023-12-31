package porori.backend.user.global.config.security.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum SecurityExceptionList{
    REQUIRED_LOGGED_IN("SS0001", HttpStatus.UNAUTHORIZED, "해당 서비스는 로그인이 필요한 서비스입니다."),
    NOT_FOUND_REFRESH_TOKEN_ERROR("SS0002", HttpStatus.INTERNAL_SERVER_ERROR, "Refresh Token이 유효하지 않습니다"),
    ACCESS_TOKEN_INVALID_ERROR("SS0003", HttpStatus.UNAUTHORIZED, "Access Token이 유효하지 않습니다");
    private final String errorCode;
    private final HttpStatus httpStatus;
    private final String message;
}
