package thistle.service.chat;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
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
}
