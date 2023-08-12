package porori.backend.user.global.interceptor;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import porori.backend.user.domain.user.domain.service.UserValidationService;
import porori.backend.user.global.config.security.dto.CustomUser;
import porori.backend.user.global.config.security.exception.AccessTokenInvalidException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@RequiredArgsConstructor
@Component
public class AuthInterceptor implements HandlerInterceptor {

    private final UserValidationService userValidationService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null && authentication.isAuthenticated()) {
                CustomUser user = (CustomUser) authentication.getPrincipal();
                String socialId = user.getAppleId();

                // socialId 를 갖는 사용자 존재 여부
                userValidationService.validateAppleId(socialId);
            }
            return true;
        } catch (Exception e) {
            throw new AccessTokenInvalidException();
        }
    }
}