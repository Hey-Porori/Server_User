package porori.backend.user.domain.user.application.validate;

import porori.backend.user.domain.user.domain.User;

public interface UserValidationService {
    User validateAppleId(String appleId);
}
