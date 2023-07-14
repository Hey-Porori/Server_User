package porori.backend.user.domain.user.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import porori.backend.user.domain.user.domain.entity.User;

public interface UserRepository extends JpaRepository<User, Long> , UserRepositoryCustom {
}
