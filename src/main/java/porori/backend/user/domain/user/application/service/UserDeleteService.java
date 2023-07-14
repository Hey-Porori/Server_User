package porori.backend.user.domain.user.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import porori.backend.user.domain.user.application.dto.req.UserRequestDto;
import porori.backend.user.domain.user.domain.entity.User;
import porori.backend.user.domain.user.domain.service.UserSaveService;
import porori.backend.user.global.config.security.utils.SecurityUtils;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserDeleteService {

    private final UserSaveService userSaveService;

    public void deleteAccount(UserRequestDto.DeleteAccountRequest deleteAccountRequest) {
        User user = SecurityUtils.getLoggedInUser();

        user.withdraw();
        userSaveService.saveUser(user);
    }
}
