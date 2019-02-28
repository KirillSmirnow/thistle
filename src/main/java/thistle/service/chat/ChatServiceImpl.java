package thistle.service.chat;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import thistle.domain.Chat;
import thistle.domain.ChatMessage;
import thistle.domain.User;
import thistle.exception.ThistleException;
import thistle.repository.ChatMessageRepository;
import thistle.repository.ChatRepository;
import thistle.repository.UserRepository;
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
    private final ChatMessageRepository chatMessageRepository;
    private final UserRepository userRepository;

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
        Chat chat = chatRepository.findById(chatId)
                .orElseThrow(() -> new ThistleException("Chat not found"));
        ChatMessage chatMessage = ChatMessage.text(chat, user, text);
        chatMessageRepository.save(chatMessage);
    }

    @Override
    public List<Message> getMessages(User user, UUID chatId, int pageIndex, int pageSize) {
        Sort sort = Sort.by("dateTime").descending();
        Pageable pageable = PageRequest.of(pageIndex, pageSize, sort);
        List<ChatMessage> messages = chatMessageRepository.findByChatId(chatId, pageable).getContent();
        return messages.stream()
                .map(message -> Message.of(message, userRepository.getOne(message.getAuthorId())))
                .collect(Collectors.toList());
    }
}
