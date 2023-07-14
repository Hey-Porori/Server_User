package porori.backend.user.domain.user.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import porori.backend.user.domain.user.application.dto.req.UserRequestDto;
import porori.backend.user.domain.user.application.dto.res.UserResponseDto;
import porori.backend.user.domain.user.domain.entity.User;
import porori.backend.user.domain.user.domain.service.UserValidationService;
import porori.backend.user.global.config.security.jwt.TokenProvider;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserInfoService {
    private final TokenProvider tokenProvider;
    private final UserValidationService userValidationService;

    public UserResponseDto.GetUserInfoResponse getUserInfo(UserRequestDto.GetUserInfoRequest getUserInfoRequest){
        Authentication authentication = tokenProvider.getAuthentication(getUserInfoRequest.getAccessToken());
        User user = userValidationService.validateAppleId(authentication.getName());
        return UserResponseDto.GetUserInfoResponse.from(user);
    }

}
