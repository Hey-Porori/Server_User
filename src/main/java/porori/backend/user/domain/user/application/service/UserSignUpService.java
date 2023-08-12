package porori.backend.user.domain.user.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import porori.backend.user.domain.user.application.dto.request.AdditionInfoRequest;
import porori.backend.user.domain.user.application.dto.request.TestSignUpRequest;
import porori.backend.user.domain.user.application.dto.response.LoginResponse;
import porori.backend.user.domain.user.application.mapper.UserMapper;
import porori.backend.user.domain.user.domain.entity.User;
import porori.backend.user.domain.user.domain.service.UserSaveService;
import porori.backend.user.domain.user.domain.service.UserValidationService;
import porori.backend.user.global.config.security.jwt.TokenUtil;
import porori.backend.user.global.config.security.utils.AuthenticationUtil;
import porori.backend.user.global.dto.TokenInfoResponse;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserSignUpService {

    private final UserAppleService appleService;
    private final UserSaveService userSaveService;
    private final UserValidationService userValidationService;
    private final TokenUtil tokenUtil;
    private final AuthenticationUtil authenticationUtil;
    private final UserMapper userMapper;

    public LoginResponse signup(AdditionInfoRequest additionInfoRequest) {
        //1. 유저 찾기
        String socialId = tokenUtil.getAppleId(additionInfoRequest.getAccessToken());
        User user = userValidationService.validateAppleId(socialId);
        //2. signUp 처리
        user.signup(additionInfoRequest);
        //3. security 처리
        AuthenticationUtil.makeAuthentication(user);
        //4. token 만들기
        TokenInfoResponse tokenResponse = tokenUtil.createToken(user, true);
        //5. refresh token 저장
        tokenUtil.storeRefreshToken(user.getAppleId(), tokenResponse);

        return LoginResponse.from(tokenResponse, true);
    }

    public LoginResponse testSignUp(TestSignUpRequest signUpRequest) {
        User user = new User(signUpRequest);
        //2. signUp 처리;
        user.updateRegistrationStatus();
        userSaveService.saveUser(user);
        //3. security 처리
        AuthenticationUtil.makeAuthentication(user);
        //4. token 만들기
        TokenInfoResponse tokenResponse = tokenUtil.createToken(user, true);
        //5. refresh token 저장
        tokenUtil.storeRefreshToken(user.getAppleId(), tokenResponse);

        return LoginResponse.from(tokenResponse, true);
    }

}
