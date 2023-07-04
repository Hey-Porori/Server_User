package porori.backend.user.global.config.security.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import porori.backend.user.domain.user.domain.User;
import porori.backend.user.global.config.security.exception.RequiredLoggedInException;
import porori.backend.user.global.config.security.service.CustomUserDetails;

import java.util.Objects;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Transactional
public class SecurityUtils {

    public static User getLoggedInUser() {
        try {
            return
                    ((CustomUserDetails) Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication()).getPrincipal()).getUser();
        } catch (NullPointerException e) {
            throw new RequiredLoggedInException();
        }
    }

}