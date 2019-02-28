package thistle.service.chat;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import thistle.service.user.Profile;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class ChatCreate {

    private final String name;
    private final List<Profile> members;
}
