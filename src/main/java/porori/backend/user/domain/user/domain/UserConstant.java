package porori.backend.user.domain.user.domain;

import lombok.Getter;

public class UserConstant {
    @Getter
    public enum Role {
        ROLE_USER, ROLE_ADMIN
    }
}
