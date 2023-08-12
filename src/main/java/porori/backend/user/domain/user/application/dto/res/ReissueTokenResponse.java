package porori.backend.user.domain.user.application.dto.res;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import porori.backend.user.global.dto.TokenInfoResponse;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReissueTokenResponse {

    private String accessToken;
    private String refreshToken;

    public static ReissueTokenResponse from(TokenInfoResponse tokenInfoResponse) {
        return ReissueTokenResponse.builder()
                .accessToken(tokenInfoResponse.getAccessToken())
                .refreshToken(tokenInfoResponse.getRefreshToken())
                .build();
    }

}
