package porori.backend.user.domain.user.presentation.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EUserResponseMessage {
    LOGIN_SUCCESS("애플 로그인을 했습니다"),
    SIGN_UP_SUCCESS("회원 가입을 완료했습니다"),
    DELETE_SUCCESS("회원 탈퇴를 하였습니다"),
    LOGOUT_SUCCESS("로그아웃을 하였습니다"),
    VALID_TOKEN("유효한 토큰입니다"),
    GET_USERINFO_SUCCESS("사용자 정보 조회를 완료했습니다"),
    GET_COMMUNITY_USERINFO_SUCCESS("커뮤니티에서 사용자 정보 조회를 완료했습니다");

    private final String message;
}

