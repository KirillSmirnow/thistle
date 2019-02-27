package thistle.service.friends;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import thistle.domain.Friendship;
import thistle.domain.User;
import thistle.repository.FriendshipRepository;
import thistle.repository.UserRepository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@DataJpaTest
@ContextConfiguration(classes = FriendsTestConfig.class)
public class FriendsServiceFriendsStateTest {

    @Autowired
    private FriendsService friendsService;
    @Autowired
    private FriendshipRepository friendshipRepository;
    @Autowired
    private UserRepository userRepository;

    private User friend1;
    private User friend2;

    /**
     * {@link #friend1}.id must be less than {@link #friend2}.id
     */
    @Before
    public void init() {
        friend1 = userRepository.save(new User("Friend1", "Friend1"));
        friend2 = userRepository.save(new User("Friend2", "Friend2"));

        Friendship friendship = Friendship.create(friend1, friend2);
        friendship.follow(friend2);
        friendshipRepository.save(friendship);

        assertTrue(friendshipRepository.find(friend1, friend2).isPresent());
        assertEquals(Friendship.Bias.MEDIUM, friendshipRepository.find(friend1, friend2).get().getBias());
    }

    @Test
    public void whenFriend1Unfollows_thenRightBias() {
        friendsService.unfollow(friend1, friend2.getId());
        assertTrue(friendshipRepository.find(friend1, friend2).isPresent());
        assertEquals(Friendship.Bias.RIGHT, friendshipRepository.find(friend1, friend2).get().getBias());
    }

    @Test
    public void whenFriend2Unfollows_thenLeftBias() {
        friendsService.unfollow(friend2, friend1.getId());
        assertTrue(friendshipRepository.find(friend1, friend2).isPresent());
        assertEquals(Friendship.Bias.LEFT, friendshipRepository.find(friend1, friend2).get().getBias());
    }
}
