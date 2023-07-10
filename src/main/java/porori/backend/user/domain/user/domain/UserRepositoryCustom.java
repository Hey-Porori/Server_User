package porori.backend.user.domain.user.domain;

import java.util.Optional;

public interface UserRepositoryCustom {

    Optional<User> findNotWithdrawByAppleId(String appleId);

}
