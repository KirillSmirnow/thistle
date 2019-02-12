package thistle.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import thistle.domain.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByAccessToken(String accessToken);
}
