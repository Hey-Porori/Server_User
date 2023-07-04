package porori.backend.user.domain.user.application.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import porori.backend.user.domain.user.application.apple.UserAppleServiceImpl;
import porori.backend.user.domain.user.application.validate.UserValidationService;
import porori.backend.user.domain.user.domain.User;
import porori.backend.user.domain.user.domain.UserRepository;
import porori.backend.user.domain.user.presentation.dto.req.UserRequestDto;
import porori.backend.user.domain.user.presentation.dto.req.UserRequestDto.DeleteAccountRequest;
import porori.backend.user.domain.user.presentation.dto.res.UserResponseDto.LoginResponse;
import porori.backend.user.global.config.security.jwt.TokenProvider;
import porori.backend.user.global.config.security.utils.SecurityUtils;
import porori.backend.user.global.dto.TokenInfoResponse;
import porori.backend.user.global.exception.ConnException;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static porori.backend.user.domain.user.domain.UserConstant.Role.ROLE_USER;
import static porori.backend.user.domain.user.presentation.constant.Process.LOGIN_SUCCESS;
import static porori.backend.user.domain.user.presentation.constant.Process.SIGN_UP_ING;


@Service
@RequiredArgsConstructor
@Transactional
public class UserAuthenticationServiceImpl implements UserAuthenticationService {

    private final UserAppleServiceImpl appleService;
    private final UserRepository userRepository;
    private final UserValidationService validateService;
    private final TokenProvider tokenProvider;

    @Override
    public LoginResponse login(UserRequestDto.LoginRequest loginRequest) {
        //1. 프론트에게 받은 액세스 토큰 이용해서 사용자 정보 가져오기
        String token = loginRequest.getToken();
        String appleId = appleService.verifyIdentityToken(token).orElseThrow(() -> new ConnException());
        User user = saveUser(appleId);
        boolean isSignedUp = user.getNickName() != null;

        //2. 스프링 시큐리티 처리
        List<GrantedAuthority> authorities = initAuthorities();
        OAuth2User userDetails = createOAuth2UserByApple(authorities, appleId);
        OAuth2AuthenticationToken auth = configureAuthentication(userDetails, authorities);

        //3. JWT 토큰 생성
        TokenInfoResponse tokenInfoResponse = tokenProvider.createToken(auth, isSignedUp, user.getUserId());
        return LoginResponse.from(tokenInfoResponse, isSignedUp ? LOGIN_SUCCESS.getMessage() : SIGN_UP_ING.getMessage(), user.getUserId());
    }

    @Override
    public LoginResponse signup(UserRequestDto.AdditionInfoRequest additionInfoRequest) {
        //추가 정보 입력 시
        //1. 프론트엔드에게 받은 (자체) 액세스 토큰 이용해서 사용자 이메일 가져오기
        Authentication authentication = tokenProvider.getAuthentication(additionInfoRequest.getAccessToken());
        User user = validateService.validateAppleId(authentication.getName());

        //2. 추가 정보 저장
        user.signup(additionInfoRequest.getName(), additionInfoRequest.getNickName(), additionInfoRequest.getPhoneNumber(), additionInfoRequest.getGender(), additionInfoRequest.getAddress(), additionInfoRequest.getImageUrl(), additionInfoRequest.getEmail());
        userRepository.save(user);

        //3. 스프링 시큐리티 처리
        List<GrantedAuthority> authorities = initAuthorities();
        OAuth2User userDetails = createOAuth2UserByUser(authorities, user);
        OAuth2AuthenticationToken auth = configureAuthentication(userDetails, authorities);

        //4. JWT 토큰 생성
        TokenInfoResponse tokenInfoResponse = tokenProvider.createToken(auth, true, user.getUserId());
        return LoginResponse.from(tokenInfoResponse, LOGIN_SUCCESS.getMessage(), user.getUserId());
    }

    @Override
    public void logout(UserRequestDto.LoginRequest loginRequest) {
        User user = SecurityUtils.getLoggedInUser();
    }

    @Override
    public void deleteAccount(DeleteAccountRequest deleteAccountRequest) {
        User user = SecurityUtils.getLoggedInUser();

        user.withdraw();
        userRepository.save(user);
    }

    public User saveUser(String appleId) {
        User user = new User(appleId, ROLE_USER);
        if (!userRepository.findNotWithdrawByAppleId(appleId).isPresent()) {
            return userRepository.save(user);
        }
        return userRepository.findNotWithdrawByAppleId(appleId).get();
    }


    public OAuth2User createOAuth2UserByUser(List<GrantedAuthority> authorities, User user) {
        Map userMap = new HashMap<String, String>();
        userMap.put("appleId", user.getAppleId());
        OAuth2User userDetails = new DefaultOAuth2User(authorities, userMap, "appleId");
        return userDetails;
    }


    private OAuth2User createOAuth2UserByApple(List<GrantedAuthority> authorities, String appleId) {
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
