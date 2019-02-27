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

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
@ContextConfiguration(classes = FriendsTestConfig.class)
public class FriendsServiceRightBiasStateTest {

    @Autowired
    private FriendsService friendsService;
    @Autowired
    private FriendshipRepository friendshipRepository;
    @Autowired
    private UserRepository userRepository;

    private User follower;
    private User followee;

    /**
     * {@link #follower}.id must be greater than {@link #followee}.id
     */
    @Before
    public void init() {
        followee = userRepository.save(new User("Followee", "Followee"));
        follower = userRepository.save(new User("Follower", "Follower"));
        friendshipRepository.save(Friendship.create(follower, followee));
        assertTrue(friendshipRepository.find(follower, followee).isPresent());
        assertEquals(Friendship.Bias.RIGHT, friendshipRepository.find(follower, followee).get().getBias());
    }

    @Test
    public void whenFolloweeAcceptsRequest_thenMediumBias() {
        friendsService.follow(followee, follower.getId());
        assertTrue(friendshipRepository.find(follower, followee).isPresent());
        assertEquals(Friendship.Bias.MEDIUM, friendshipRepository.find(follower, followee).get().getBias());
    }

    @Test
    public void whenFollowerCancelsRequest_thenFriendshipNotExists() {
        friendsService.unfollow(follower, followee.getId());
        assertFalse(friendshipRepository.find(follower, followee).isPresent());
    }
}
