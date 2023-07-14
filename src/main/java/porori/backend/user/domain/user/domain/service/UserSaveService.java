package porori.backend.user.domain.user.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import porori.backend.user.domain.user.domain.entity.User;
import porori.backend.user.domain.user.domain.repository.UserRepository;

import javax.transaction.Transactional;

import static porori.backend.user.domain.user.domain.entity.UserConstant.Role.ROLE_USER;

@Service
@RequiredArgsConstructor
@Transactional
public class UserSaveService {

    private final UserRepository userRepository;


    public User saveUser(String appleId) {
        User user = new User(appleId, ROLE_USER);
        if (!userRepository.findNotWithdrawByAppleId(appleId).isPresent()) {
            return userRepository.save(user);
        }
        return userRepository.findNotWithdrawByAppleId(appleId).get();
    }

    public void saveUser(User user){
        userRepository.save(user);
    }
}
