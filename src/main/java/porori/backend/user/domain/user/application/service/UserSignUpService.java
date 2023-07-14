package porori.backend.user.domain.user.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
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

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static porori.backend.user.domain.user.domain.entity.UserConstant.Role.ROLE_USER;
import static porori.backend.user.domain.user.presentation.constant.Process.LOGIN_SUCCESS;

@Service
@RequiredArgsConstructor
@Transactional
public class UserSignUpService {

    private final UserAppleService appleService;
    private final UserSaveService userSaveService;
    private final UserValidationService userValidationService;
    private final TokenProvider tokenProvider;
    private final UserMapper userMapper;

    public UserResponseDto.LoginResponse signup(UserRequestDto.AdditionInfoRequest additionInfoRequest) {
        //추가 정보 입력 시
        //1. 프론트엔드에게 받은 (자체) 액세스 토큰 이용해서 사용자 이메일 가져오기
        Authentication authentication = tokenProvider.getAuthentication(additionInfoRequest.getAccessToken());
        User user = userValidationService.validateAppleId(authentication.getName());

        //2. 추가 정보 저장
        user.signup(additionInfoRequest.getName(), additionInfoRequest.getNickName(), additionInfoRequest.getPhoneNumber(), additionInfoRequest.getGender(), additionInfoRequest.getAddress(), additionInfoRequest.getImageUrl(), additionInfoRequest.getEmail());
        userSaveService.saveUser(user);

        //3. 스프링 시큐리티 처리
        List<GrantedAuthority> authorities = userMapper.initAuthorities();
        OAuth2User userDetails = userMapper.createOAuth2UserByUser(authorities, user);
        OAuth2AuthenticationToken auth = userMapper.configureAuthentication(userDetails, authorities);

        //4. JWT 토큰 생성
        TokenInfoResponse tokenInfoResponse = tokenProvider.createToken(auth, true, user.getUserId());
        return UserResponseDto.LoginResponse.from(tokenInfoResponse, LOGIN_SUCCESS.getMessage(), user.getUserId());
    }

    public UserResponseDto.LoginResponse testSignUp(UserRequestDto.TestSignUpRequest signUpRequest) {
        User user=new User(signUpRequest);
        userSaveService.saveUser(user);

        List<GrantedAuthority> authorities = userMapper.initAuthorities();
        OAuth2User userDetails = userMapper.createOAuth2UserByUser(authorities, user);
        OAuth2AuthenticationToken auth = userMapper.configureAuthentication(userDetails, authorities);

        TokenInfoResponse tokenInfoResponse = tokenProvider.createToken(auth, true, user.getUserId());
        return UserResponseDto.LoginResponse.from(tokenInfoResponse, LOGIN_SUCCESS.getMessage(), user.getUserId());
    }

}
