package porori.backend.user.global.config.security.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import porori.backend.user.domain.user.domain.entity.User;
import porori.backend.user.domain.user.domain.service.UserValidationService;
import porori.backend.user.global.config.security.jwt.constants.JwtExceptionList;
import porori.backend.user.global.config.security.utils.AuthenticationUtil;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    public static final String AUTHORIZATION_HEADER = "Authorization";
    private final TokenUtil tokenUtil;
    private final UserValidationService userValidationService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException, JwtException {
        String token = resolveToken(request);
        String requestURI = request.getRequestURI();
        try {
            if (StringUtils.hasText(token) && tokenUtil.verifyToken(token)) {
                boolean isAdditionalInfoProvided = tokenUtil.getAdditionalInfoProvided(token);
                if (isAdditionalInfoProvided) {
                    // 토큰 파싱해서 socialId 정보 가져오기
                    String appleId = tokenUtil.getAppleId(token);
                    User user = userValidationService.validateAppleId(appleId);

                    // 이메일로 Authentication 정보 생성
                    AuthenticationUtil.makeAuthentication(user);
                    log.debug("Security Context에 '{}' 인증 정보를 저장했습니다, uri: {}", user.getAppleId(), requestURI);
                } else {
                    request.setAttribute("exception", JwtExceptionList.ADDITIONAL_REQUIRED_TOKEN.getErrorCode());
                }
            }
        } catch (SecurityException | MalformedJwtException e) {
            request.setAttribute("exception", JwtExceptionList.MAL_FORMED_TOKEN.getErrorCode());
        } catch (ExpiredJwtException e) {
            request.setAttribute("exception", JwtExceptionList.EXPIRED_TOKEN.getErrorCode());
            log.info("JwtAuthFilter: Caught ExpiredJwtException");
            log.info(String.valueOf(request.getAttribute("exception")));
        } catch (UnsupportedJwtException e) {
            request.setAttribute("exception", JwtExceptionList.UNSUPPORTED_TOKEN.getErrorCode());
        } catch (IllegalArgumentException e) {
            request.setAttribute("exception", JwtExceptionList.ILLEGAL_TOKEN.getErrorCode());
        } catch (Exception e) {
            log.error("================================================");
            log.error("JwtFilter - doFilterInternal() 오류발생");
            log.error("token : {}", token);
            log.error("Exception Message : {}", e.getMessage());
            log.error("Exception StackTrace : {");
            e.printStackTrace();
            log.error("}");
            log.error("================================================");
            request.setAttribute("exception", JwtExceptionList.UNKNOWN_ERROR.getErrorCode());
        }
        filterChain.doFilter(request, response);
    }

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}