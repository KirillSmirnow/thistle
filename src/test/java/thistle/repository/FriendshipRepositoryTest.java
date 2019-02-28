package thistle.repository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import thistle.domain.Friendship;
import thistle.domain.User;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@DataJpaTest
public class FriendshipRepositoryTest {

    @Autowired
    private FriendshipRepository friendshipRepository;
    @Autowired
    private UserRepository userRepository;

    private User user;
    private User follower1;
    private User follower2;
    private User followee1;
    private User followee2;
    private User friend1;
    private User friend2;

    @Before
    public void init() {
        user = userRepository.save(new User("User", "User"));
        follower1 = userRepository.save(new User("Follower1", "Follower1"));
        follower2 = userRepository.save(new User("Follower2", "Follower2"));
        followee1 = userRepository.save(new User("Followee1", "Followee1"));
        followee2 = userRepository.save(new User("Followee2", "Followee2"));
        friend1 = userRepository.save(new User("Friend1", "Friend1"));
        friend2 = userRepository.save(new User("Friend2", "Friend2"));

        friendshipRepository.save(Friendship.create(follower1, user));
        friendshipRepository.save(Friendship.create(follower2, user));

        friendshipRepository.save(Friendship.create(user, followee1));
        friendshipRepository.save(Friendship.create(user, followee2));

        Friendship friendship1 = Friendship.create(user, friend1);
        Friendship friendship2 = Friendship.create(friend2, user);
        friendship1.followBy(friend1);
        friendship2.followBy(user);
        friendshipRepository.save(friendship1);
        friendshipRepository.save(friendship2);
    }

    @Test
    public void whenFindByUsersInDifferentOrder_thenExists() {
        assertTrue(friendshipRepository.find(user, followee2).isPresent());
        assertTrue(friendshipRepository.find(followee2, user).isPresent());
    }

    @Test
    public void findFriends() {
        List<User> friends = friendshipRepository.findFriends(user).stream()
                .map(friendship -> friendship.getUserExcluding(user))
                .collect(Collectors.toList());

        assertEquals(2, friends.size());
        assertTrue(friends.contains(friend1));
        assertTrue(friends.contains(friend2));
    }

    @Test
    public void findIncomingRequests() {
        List<User> incomingRequests = friendshipRepository.findIncomingRequests(user).stream()
                .map(friendship -> friendship.getUserExcluding(user))
                .collect(Collectors.toList());

        assertEquals(2, incomingRequests.size());
        assertTrue(incomingRequests.contains(follower1));
        assertTrue(incomingRequests.contains(follower2));
    }

    @Test
    public void findOutgoingRequests() {
        List<User> outgoingRequests = friendshipRepository.findOutgoingRequests(user).stream()
                .map(friendship -> friendship.getUserExcluding(user))
                .collect(Collectors.toList());

        assertEquals(2, outgoingRequests.size());
        assertTrue(outgoingRequests.contains(followee1));
        assertTrue(outgoingRequests.contains(followee2));
    }
}
