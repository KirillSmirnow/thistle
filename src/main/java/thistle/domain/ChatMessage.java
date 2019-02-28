package thistle.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

import static java.util.UUID.randomUUID;

@Getter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class ChatMessage {

    private UUID id;
    private UUID chatId;
    private int authorId;
    private LocalDateTime dateTime;
    private String text;

    private ChatMessage(UUID chatId, int authorId, String text) {
        this.id = randomUUID();
        this.chatId = chatId;
        this.authorId = authorId;
        this.dateTime = LocalDateTime.now();
        this.text = text;
    }

    public static ChatMessage text(Chat chat, User author, String text) {
        return new ChatMessage(chat.getId(), author.getId(), text);
    }
}
