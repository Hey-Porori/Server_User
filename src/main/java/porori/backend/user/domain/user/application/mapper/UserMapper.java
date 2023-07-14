package porori.backend.user.domain.user.application.mapper;

import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import porori.backend.user.domain.user.domain.entity.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static porori.backend.user.domain.user.domain.entity.UserConstant.Role.ROLE_USER;

@Service
public class UserMapper {
    public OAuth2User createOAuth2UserByUser(List<GrantedAuthority> authorities, User user) {
        Map userMap = new HashMap<String, String>();
        userMap.put("appleId", user.getAppleId());
        OAuth2User userDetails = new DefaultOAuth2User(authorities, userMap, "appleId");
        return userDetails;
    }


    public OAuth2User createOAuth2UserByApple(List<GrantedAuthority> authorities, String appleId) {
        Map userMap = new HashMap<String, String>();
        userMap.put("appleId", appleId);
        authorities.add(new SimpleGrantedAuthority(String.valueOf(ROLE_USER)));
        OAuth2User userDetails = new DefaultOAuth2User(authorities, userMap, "appleId");
        return userDetails;
    }


    public List<GrantedAuthority> initAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(String.valueOf(ROLE_USER)));
        return authorities;
    }

    public OAuth2AuthenticationToken configureAuthentication(OAuth2User userDetails, List<GrantedAuthority> authorities) {
        OAuth2AuthenticationToken auth = new OAuth2AuthenticationToken(userDetails, authorities, "email");
        auth.setDetails(userDetails);
        SecurityContextHolder.getContext().setAuthentication(auth);
        return auth;
    }
}
