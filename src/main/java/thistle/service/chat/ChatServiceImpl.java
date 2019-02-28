package thistle.service.chat;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import thistle.domain.Chat;
import thistle.domain.User;
import thistle.repository.ChatRepository;
import thistle.security.PseudoUser;
import thistle.service.user.Profile;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final ChatRepository chatRepository;

    @Override
    public void create(User user, ChatCreate chatCreate) {
        List<User> members = chatCreate.getMembers() == null ? new ArrayList<>() : chatCreate.getMembers().stream()
                .map(Profile::getId)
                .map(PseudoUser::of)
                .collect(Collectors.toList());
        // TODO: 28.02.19 check if members are user's friends
        chatRepository.save(new Chat(user, chatCreate.getName(), members));
    }

    @Override
    public List<ChatDto> getChats(User user) {
        List<Chat> chats = chatRepository.findByMembers(user);
        return chats.stream()
                .map(ChatDto::of)
                .collect(Collectors.toList());
    }

    @Override
    public void sendMessage(User user, UUID chatId, String text) {
    }

    @Override
    public List<Message> getMessages(User user, UUID chatId) {
        return null;
    }
}
