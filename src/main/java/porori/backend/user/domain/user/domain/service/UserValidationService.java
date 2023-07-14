package porori.backend.user.domain.user.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import porori.backend.user.domain.user.domain.entity.User;
import porori.backend.user.domain.user.domain.repository.UserRepository;
import porori.backend.user.global.exception.NotFoundAppleIdException;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserValidationService {

    private final UserRepository userRepository;
    public User validateAppleId(String appleId) {
        return this.userRepository.findNotWithdrawByAppleId(appleId).orElseThrow(() -> new NotFoundAppleIdException());
    }
}
