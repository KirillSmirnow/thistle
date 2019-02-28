package thistle.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import thistle.domain.Chat;
import thistle.domain.User;

import java.util.List;
import java.util.UUID;

public interface ChatRepository extends JpaRepository<Chat, UUID> {

    List<Chat> findByMembers(User member);
}
