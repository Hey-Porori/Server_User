package porori.backend.user.domain.user.application.apple;

import java.util.Optional;

public interface UserAppleService {
    Optional<String> verifyIdentityToken(String identityToken);
}
