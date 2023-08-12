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
import porori.backend.user.domain.user.domain.entity.UserConstant;
import porori.backend.user.domain.user.domain.service.UserSaveService;
import porori.backend.user.domain.user.domain.service.UserValidationService;
import porori.backend.user.global.config.security.jwt.TokenUtil;
import porori.backend.user.global.config.security.utils.AuthenticationUtil;
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

    private final TokenUtil tokenUtil;
    public final UserMapper userMapper;

    public UserResponseDto.LoginResponse login(UserRequestDto.LoginRequest loginRequest) {
        //1. 프론트에게 받은 액세스 토큰 이용해서 사용자 정보 가져오기
        String token = loginRequest.getToken();
        String appleId = appleService.verifyIdentityToken(token).orElseThrow(() -> new ConnException());
        User user = userSaveService.saveUser(appleId);
        boolean registrationStatus = user.getRegistrationStatus().equals(UserConstant.RegistrationStatus.COMPLETED);
        //3. security 처리
        AuthenticationUtil.makeAuthentication(user);
        //4. token 만들기
        TokenInfoResponse tokenResponse = tokenUtil.createToken(user, registrationStatus);
        //5. refresh token 저장
        tokenUtil.storeRefreshToken(user.getAppleId(), tokenResponse);

        return UserResponseDto.LoginResponse.from(tokenResponse, registrationStatus);
    }


    public UserResponseDto.LoginResponse testLogin(UserRequestDto.TestLoginRequest testLoginRequest) {
        User user = userValidationService.validateAppleId(testLoginRequest.getAppleId());

        boolean registrationStatus = user.getRegistrationStatus().equals(UserConstant.RegistrationStatus.COMPLETED);
        //3. security 처리
        AuthenticationUtil.makeAuthentication(user);
        //4. token 만들기
        TokenInfoResponse tokenResponse = tokenUtil.createToken(user, registrationStatus);
        //5. refresh token 저장
        tokenUtil.storeRefreshToken(user.getAppleId(), tokenResponse);

        return UserResponseDto.LoginResponse.from(tokenResponse, registrationStatus);
    }
}
