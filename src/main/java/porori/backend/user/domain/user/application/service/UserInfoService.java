package porori.backend.user.domain.user.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import porori.backend.user.domain.user.application.dto.request.GetCommunityUserInfoRequest;
import porori.backend.user.domain.user.application.dto.response.CommunityUserInfoResponse;
import porori.backend.user.domain.user.application.dto.response.UserInfoResponse;
import porori.backend.user.domain.user.domain.entity.User;
import porori.backend.user.domain.user.domain.service.UserQueryService;
import porori.backend.user.domain.user.domain.service.UserValidationService;
import porori.backend.user.global.config.security.jwt.TokenUtil;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class UserInfoService {
    private final TokenUtil tokenUtil;
    private final UserValidationService userValidationService;
    private final UserQueryService userQueryService;

    public UserInfoResponse getUserInfo(String appleId) {
        User user = userValidationService.validateAppleId(appleId);
        return UserInfoResponse.from(user);
    }

    public CommunityUserInfoResponse getCommunityUserInfo(List<Long> userIdList) {
        return userQueryService.getCommunityUserInfoByUserIdList(userIdList);
    }

}
