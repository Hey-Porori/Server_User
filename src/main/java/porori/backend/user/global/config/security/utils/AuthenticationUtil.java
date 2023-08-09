package porori.backend.user.global.config.security.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import porori.backend.user.domain.user.domain.entity.User;
import porori.backend.user.global.config.security.dto.CustomUser;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class AuthenticationUtil {


    public static String getCurrentUserEmail() {
        CustomUser user = (CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user.getEmail();
    }

    public static String getCurrentUserSocialId() {
        CustomUser user = (CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user.getAppleId();
    }

    public static Authentication getAuthentication(CustomUser user) {

        List<GrantedAuthority> grantedAuthorities = user.getRoles().stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        return new UsernamePasswordAuthenticationToken(user, "",
                grantedAuthorities);
    }

    public static void makeAuthentication(User user) {
        // Authentication 정보 만들기
        CustomUser customUser = CustomUser.builder()
                .appleId(user.getAppleId())
                .email(user.getEmail())
                .roles(Arrays.asList(user.getRole().toString()))
                .build();

        // ContextHolder 에 Authentication 정보 저장
        Authentication auth = AuthenticationUtil.getAuthentication(customUser);
        SecurityContextHolder.getContext().setAuthentication(auth);
    }
}



