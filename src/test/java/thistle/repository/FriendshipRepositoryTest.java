package thistle.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import thistle.domain.Friendship;
import thistle.domain.User;

import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@DataJpaTest
public class FriendshipRepositoryTest {

    @Autowired
    private FriendshipRepository friendshipRepository;
    @Autowired
    private UserRepository userRepository;

    @Test
    public void givenFriendship_whenFindByUsersInDifferentOrder_thenExists() {
        User follower = userRepository.save(new User("Follower", "Follower"));
        User followee = userRepository.save(new User("Followee", "Followee"));
        friendshipRepository.save(Friendship.create(follower, followee));
        assertTrue(friendshipRepository.find(follower, followee).isPresent());
        assertTrue(friendshipRepository.find(followee, follower).isPresent());
    }
}
