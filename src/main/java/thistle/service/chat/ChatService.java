package thistle.service.chat;

import thistle.domain.User;

import java.util.List;
import java.util.UUID;

public interface ChatService {

    void create(User user, ChatCreate chatCreate);

    List<ChatDto> getChats(User user);

    void sendMessage(User user, UUID chatId, String text);

    List<Message> getMessages(User user, UUID chatId);
}
