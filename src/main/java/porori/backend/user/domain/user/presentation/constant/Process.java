package porori.backend.user.domain.user.presentation.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Process {
    SIGN_UP_ING("회원가입 중-추가 정보를 입력해주세요."),
    SIGN_UP_SUCCESS("회원가입이 완료되었습니다"),
    LOGIN_SUCCESS("로그인이 완료되었습니다");

    private final String message;
}
