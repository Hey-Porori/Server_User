package porori.backend.user.domain.user.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import porori.backend.user.domain.user.domain.entity.Location;

public interface LocationRepository extends JpaRepository<Location, Long> {
}
