package thistle.service.chat;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import thistle.domain.Chat;
import thistle.service.user.Profile;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
public class ChatDto {

    private final UUID id;
    private final String name;
    private final Profile admin;
    private final List<Profile> members;

    public static ChatDto of(Chat chat) {
        List<Profile> members = chat.getMembers().stream()
                .map(Profile::of)
                .collect(Collectors.toList());
        return new ChatDto(chat.getId(), chat.getName(), Profile.of(chat.getAdmin()), members);
    }
}
