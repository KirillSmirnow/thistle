package thistle.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import thistle.domain.ChatMessage;

import java.util.UUID;

public interface ChatMessageRepository extends MongoRepository<ChatMessage, UUID> {

    Page<ChatMessage> findByChatId(UUID id, Pageable pageable);
}
