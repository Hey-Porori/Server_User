package porori.backend.user.domain.user.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import porori.backend.user.domain.user.application.dto.req.UserRequestDto;
import porori.backend.user.domain.user.application.dto.res.UserResponseDto;
import porori.backend.user.domain.user.application.dto.res.UserResponseDto.GetCommunityUserInfoResponse;
import porori.backend.user.domain.user.domain.entity.User;
import porori.backend.user.domain.user.domain.service.UserQueryService;
import porori.backend.user.domain.user.domain.service.UserValidationService;
import porori.backend.user.global.config.security.jwt.TokenUtil;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserInfoService {
    private final TokenUtil tokenUtil;
    private final UserValidationService userValidationService;
    private final UserQueryService userQueryService;

    public UserResponseDto.GetUserInfoResponse getUserInfo(String appleId) {
        User user = userValidationService.validateAppleId(appleId);
        return UserResponseDto.GetUserInfoResponse.from(user);
    }

    public GetCommunityUserInfoResponse getCommunityUserInfo(UserRequestDto.GetCommunityUserInfoRequest getCommunityUserInfoRequest) {
        return userQueryService.getCommunityUserInfoByUserIdList(getCommunityUserInfoRequest);
    }

}
