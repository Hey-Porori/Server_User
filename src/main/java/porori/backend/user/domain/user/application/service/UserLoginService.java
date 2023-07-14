package porori.backend.user.domain.user.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import porori.backend.user.domain.user.application.dto.req.UserRequestDto;
import porori.backend.user.domain.user.application.dto.res.UserResponseDto;
import porori.backend.user.domain.user.application.mapper.UserMapper;
import porori.backend.user.domain.user.domain.entity.User;
import porori.backend.user.domain.user.domain.service.UserSaveService;
import porori.backend.user.domain.user.domain.service.UserValidationService;
import porori.backend.user.global.config.security.jwt.TokenProvider;
import porori.backend.user.global.dto.TokenInfoResponse;
import porori.backend.user.global.exception.ConnException;

import javax.transaction.Transactional;
import java.util.List;

import static porori.backend.user.domain.user.presentation.constant.Process.LOGIN_SUCCESS;
import static porori.backend.user.domain.user.presentation.constant.Process.SIGN_UP_ING;

@Service
@RequiredArgsConstructor
@Transactional
public class UserLoginService {

    private final UserAppleService appleService;
    private final UserSaveService userSaveService;
    private final UserValidationService userValidationService;

    private final TokenProvider tokenProvider;
    public final UserMapper userMapper;

    public UserResponseDto.LoginResponse login(UserRequestDto.LoginRequest loginRequest) {
        //1. 프론트에게 받은 액세스 토큰 이용해서 사용자 정보 가져오기
        String token = loginRequest.getToken();
        String appleId = appleService.verifyIdentityToken(token).orElseThrow(() -> new ConnException());
        User user = userSaveService.saveUser(appleId);
        boolean isSignedUp = user.getNickName() != null;

        //2. 스프링 시큐리티 처리
        List<GrantedAuthority> authorities = userMapper.initAuthorities();
        OAuth2User userDetails = userMapper.createOAuth2UserByApple(authorities, appleId);
        OAuth2AuthenticationToken auth = userMapper.configureAuthentication(userDetails, authorities);

        //3. JWT 토큰 생성
        TokenInfoResponse tokenInfoResponse = tokenProvider.createToken(auth, isSignedUp, user.getUserId());
        return UserResponseDto.LoginResponse.from(tokenInfoResponse, isSignedUp ? LOGIN_SUCCESS.getMessage() : SIGN_UP_ING.getMessage(), user.getUserId());
    }


    public UserResponseDto.LoginResponse testLogin(UserRequestDto.TestLoginRequest testLoginRequest) {
        User user = userValidationService.validateAppleId(testLoginRequest.getAppleId());


        List<GrantedAuthority> authorities = userMapper.initAuthorities();
        OAuth2User userDetails = userMapper.createOAuth2UserByUser(authorities, user);
        OAuth2AuthenticationToken auth = userMapper.configureAuthentication(userDetails, authorities);

        TokenInfoResponse tokenInfoResponse = tokenProvider.createToken(auth, true, user.getUserId());
        return UserResponseDto.LoginResponse.from(tokenInfoResponse, LOGIN_SUCCESS.getMessage(), user.getUserId());
    }
}
