package thistle.service.friends;

import thistle.domain.User;

public interface FriendsService {

    void addVkFriend(User user, long targetVkId);

    void follow(User user, int targetId);

    void unfollow(User user, int targetId);

    Friendships getFriendships(User user);
}
