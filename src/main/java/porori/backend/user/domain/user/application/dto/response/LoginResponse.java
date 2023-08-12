package porori.backend.user.domain.user.application.dto.response;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import porori.backend.user.global.dto.TokenInfoResponse;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "로그인을 위한 응답 객체")
public class LoginResponse {
    private String accessToken;
    private String refreshToken;
    private boolean registrationStatus;

    public static LoginResponse from(TokenInfoResponse tokenInfoResponse, boolean registrationStatus) {
        return LoginResponse.builder()
                .accessToken(tokenInfoResponse.getAccessToken())
                .refreshToken(tokenInfoResponse.getRefreshToken())
                .registrationStatus(registrationStatus)
                .build();
    }
}
