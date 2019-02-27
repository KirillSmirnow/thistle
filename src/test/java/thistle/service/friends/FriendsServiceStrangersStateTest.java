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
public class FriendsServiceStrangersStateTest {

    @Autowired
    private FriendsService friendsService;
    @Autowired
    private FriendshipRepository friendshipRepository;
    @Autowired
    private UserRepository userRepository;

    private User follower;
    private User followee;
    private Friendship.Bias expectedBias;

    @Before
    public void init() {
        follower = userRepository.save(new User("Follower", "Follower"));
        followee = userRepository.save(new User("Followee", "Followee"));
        expectedBias = follower.getId() < followee.getId() ? Friendship.Bias.LEFT : Friendship.Bias.RIGHT;
    }

    @Test
    public void whenFollow_thenFriendshipExistsWithExpectedBias() {
        friendsService.follow(follower, followee.getId());
        assertTrue(friendshipRepository.find(follower, followee).isPresent());
        assertEquals(expectedBias, friendshipRepository.find(follower, followee).get().getBias());
    }
}
