package thistle.service.chat;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import thistle.domain.ChatMessage;
import thistle.domain.User;
import thistle.service.user.Profile;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@RequiredArgsConstructor
public class Message {

    private final UUID id;
    private final Profile author;
    private final LocalDateTime dateTime;
    private final String text;

    public static Message of(ChatMessage message, User author) {
        Profile authorProfile = author == null ? null : Profile.of(author);
        return new Message(message.getId(), authorProfile, message.getDateTime(), message.getText());
    }
}
