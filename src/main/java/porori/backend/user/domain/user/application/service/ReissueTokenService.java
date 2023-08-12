package porori.backend.user.domain.user.application.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import porori.backend.user.domain.user.application.dto.response.ReissueTokenResponse;
import porori.backend.user.global.config.security.exception.NotFoundRefreshTokenException;
import porori.backend.user.global.config.security.jwt.TokenUtil;
import porori.backend.user.global.dto.TokenInfoResponse;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ReissueTokenService {

    private final TokenUtil tokenUtil;

    public ReissueTokenResponse reissueToken(String token) {
        // refresh 토큰이 유효한지 확인
        if (token != null && tokenUtil.verifyToken(token)) {

            // 토큰 새로 받아오기
            TokenInfoResponse newToken = tokenUtil.tokenReissue(token);

            return ReissueTokenResponse.from(newToken);
        }
        throw new NotFoundRefreshTokenException();
    }
}
