package porori.backend.user.domain.user.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import porori.backend.user.domain.user.domain.entity.User;
import porori.backend.user.domain.user.domain.service.UserValidationService;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserDeleteService {

    private final UserValidationService userValidationService;

    public void deleteAccount(String appleId) {
        User user = userValidationService.validateAppleId(appleId);
        user.withdraw();
    }
}
