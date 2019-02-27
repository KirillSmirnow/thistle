package thistle.service.friends;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import thistle.service.user.Profile;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class Friendships {

    private final List<Profile> friends;
    private final List<Profile> incomingRequests;
    private final List<Profile> outgoingRequests;
}
