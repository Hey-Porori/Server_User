package porori.backend.user.domain.user.presentation.dto.res;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import porori.backend.user.global.dto.TokenInfoResponse;

public class UserResponseDto {



    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @ApiModel(description = "로그인을 위한 응답 객체")
    public static class LoginResponse {
        private String accessToken;
        private String refreshToken;
        private Long userId;
        private String process;

        public static LoginResponse from(TokenInfoResponse tokenInfoResponse, String process, Long userId) {
            return LoginResponse.builder()
                    .accessToken(tokenInfoResponse.getAccessToken())
                    .refreshToken(tokenInfoResponse.getRefreshToken())
                    .process(process)
                    .userId(userId)
                    .build();
        }
    }
}
