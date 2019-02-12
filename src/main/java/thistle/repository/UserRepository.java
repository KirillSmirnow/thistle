package thistle.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import thistle.domain.User;

public interface UserRepository extends JpaRepository<User, Integer> {
}
