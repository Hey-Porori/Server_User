package porori.backend.user.domain.user.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import porori.backend.user.domain.user.application.dto.req.UserRequestDto;
import porori.backend.user.domain.user.application.dto.res.UserResponseDto;
import porori.backend.user.domain.user.domain.repository.UserRepository;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class UserQueryService {
    private final UserRepository userRepository;

    public UserResponseDto.GetCommunityUserInfoResponse getCommunityUserInfoByUserIdList(UserRequestDto.GetCommunityUserInfoRequest getCommunityUserInfoRequest){
       return userRepository.findCommunityUserInfoByUserIdList(getCommunityUserInfoRequest.getUserIdList());
    }
}
