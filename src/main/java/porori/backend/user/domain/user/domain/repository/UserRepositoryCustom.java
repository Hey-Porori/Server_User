package porori.backend.user.domain.user.domain.repository;

import porori.backend.user.domain.user.application.dto.res.UserResponseDto.GetCommunityUserInfoResponse;
import porori.backend.user.domain.user.domain.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserRepositoryCustom {
    Optional<User> findNotWithdrawByAppleId(String appleId);

    GetCommunityUserInfoResponse findCommunityUserInfoByUserIdList(List<Long> userIdList);
}
