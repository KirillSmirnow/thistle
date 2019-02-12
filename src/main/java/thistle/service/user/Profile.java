package thistle.service.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import thistle.domain.User;

@Getter
@RequiredArgsConstructor
public class Profile {

    private final int id;
    private final String firstName;
    private final String lastName;

    public static Profile of(User user) {
        return new Profile(user.getId(), user.getFirstName(), user.getLastName());
    }
}
