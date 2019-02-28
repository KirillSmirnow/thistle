package thistle.service.friends;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import thistle.domain.Friendship;
import thistle.domain.User;
import thistle.domain.Vk;
import thistle.exception.ThistleException;
import thistle.repository.FriendshipRepository;
import thistle.repository.UserRepository;
import thistle.repository.VkRepository;
import thistle.service.user.Profile;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FriendsServiceImpl implements FriendsService {

    private final FriendshipRepository friendshipRepository;
    private final UserRepository userRepository;
    private final VkRepository vkRepository;

    private static List<Profile> friendshipsToProfiles(List<Friendship> friendships, User user) {
        return friendships.stream()
                .map(friendship -> friendship.getUserExcluding(user))
                .map(Profile::of)
                .collect(Collectors.toList());
    }

    @Override
    public void addVkFriend(User user, long targetVkId) {
        Vk vk = vkRepository.findById(targetVkId)
                .orElseThrow(() -> new ThistleException("This user isn't yet registered on Thistle"));
        follow(user, vk.getUser().getId());
    }

    @Override
    public void follow(User user, int targetId) {
        User target = getUser(targetId);
        Optional<Friendship> friendshipOpt = friendshipRepository.find(user, target);
        if (friendshipOpt.isPresent()) {
            Friendship friendship = friendshipOpt.get();
            friendship.followBy(user);
            friendshipRepository.save(friendship);
        } else {
            friendshipRepository.save(Friendship.create(user, target));
        }
    }

    @Override
    public void unfollow(User user, int targetId) {
        User target = getUser(targetId);
        friendshipRepository.find(user, target).ifPresent(friendship -> {
            boolean mustDestroy = friendship.unfollowBy(user);
            if (mustDestroy) {
                friendshipRepository.delete(friendship);
            } else {
                friendshipRepository.save(friendship);
            }
        });
    }

    @Override
    public Friendships getFriendships(User user) {
        return new Friendships(
                friendshipsToProfiles(friendshipRepository.findFriends(user), user),
                friendshipsToProfiles(friendshipRepository.findIncomingRequests(user), user),
                friendshipsToProfiles(friendshipRepository.findOutgoingRequests(user), user)
        );
    }

    private User getUser(int id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ThistleException("User not found"));
    }
}
