package thistle.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import thistle.domain.Friendship;

public interface FriendshipRepository extends JpaRepository<Friendship, Integer> {
}
