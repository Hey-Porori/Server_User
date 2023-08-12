package porori.backend.user.domain.user.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import porori.backend.user.domain.user.application.dto.request.GetCommunityUserInfoRequest;
import porori.backend.user.domain.user.application.dto.response.CommunityUserInfoResponse;
import porori.backend.user.domain.user.domain.repository.UserRepository;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class UserQueryService {
    private final UserRepository userRepository;

    public CommunityUserInfoResponse getCommunityUserInfoByUserIdList(List<Long> userIdList) {
        return userRepository.findCommunityUserInfoByUserIdList(userIdList);
    }
}
