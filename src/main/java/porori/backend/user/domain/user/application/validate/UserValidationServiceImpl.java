package porori.backend.user.domain.user.application.validate;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import porori.backend.user.domain.user.domain.User;
import porori.backend.user.domain.user.domain.UserRepository;
import porori.backend.user.global.exception.NotFoundAppleIdException;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserValidationServiceImpl implements UserValidationService {

    private final UserRepository userRepository;

    @Override
    public User validateAppleId(String appleId) {
        return this.userRepository.findNotWithdrawByAppleId(appleId).orElseThrow(() -> new NotFoundAppleIdException());
    }

}
