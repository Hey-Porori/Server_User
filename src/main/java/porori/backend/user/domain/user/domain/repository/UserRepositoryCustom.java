package porori.backend.user.domain.user.domain.repository;

import porori.backend.user.domain.user.application.dto.response.CommunityUserInfoResponse;
import porori.backend.user.domain.user.application.dto.response.UserLocationResponse;
import porori.backend.user.domain.user.domain.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserRepositoryCustom {
    Optional<User> findNotWithdrawByAppleId(String appleId);

    CommunityUserInfoResponse findCommunityUserInfoByUserIdList(List<Long> userIdList);
}
