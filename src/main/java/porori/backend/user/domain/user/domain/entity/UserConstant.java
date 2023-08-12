package porori.backend.user.domain.user.domain.entity;

import lombok.Getter;

public class UserConstant {
    @Getter
    public enum Role {
        ROLE_USER, ROLE_ADMIN
    }

    @Getter
    public enum RegistrationStatus {
        COMPLETED, UNCOMPLETED;
    }
}
