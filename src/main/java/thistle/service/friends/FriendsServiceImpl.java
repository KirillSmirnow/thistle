package thistle.service.friends;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import thistle.domain.Friendship;
import thistle.domain.User;
import thistle.exception.ThistleException;
import thistle.repository.FriendshipRepository;
import thistle.repository.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FriendsServiceImpl implements FriendsService {

    private final FriendshipRepository friendshipRepository;
    private final UserRepository userRepository;

    @Override
    public void follow(User user, int targetId) {
        User target = getUser(targetId);
        Optional<Friendship> friendshipOpt = friendshipRepository.find(user, target);
        if (friendshipOpt.isPresent()) {
            Friendship friendship = friendshipOpt.get();
            friendship.follow(user);
            friendshipRepository.save(friendship);
        } else {
            friendshipRepository.save(Friendship.create(user, target));
        }
    }

    @Override
    public void unfollow(User user, int targetId) {
        User target = getUser(targetId);
        friendshipRepository.find(user, target).ifPresent(friendship -> {
            boolean mustDestroy = friendship.unfollow(user);
            if (mustDestroy) {
                friendshipRepository.delete(friendship);
            } else {
                friendshipRepository.save(friendship);
            }
        });
    }

    @Override
    public Friendships getFriendships(User user) {
        return null;
    }

    private User getUser(int id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ThistleException("User not found"));
    }
}
