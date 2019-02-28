package thistle.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import thistle.security.PseudoUser;
import thistle.service.friends.FriendsService;
import thistle.service.friends.Friendships;

@RestController
@RequiredArgsConstructor
public class FriendsController {

    private final FriendsService friendsService;

    @PutMapping("/api/friends/{targetId}/follow")
    public void follow(@PathVariable int targetId, Authentication auth) {
        friendsService.follow(PseudoUser.of(auth.getName()), targetId);
    }

    @PutMapping("/api/friends/{targetId}/unfollow")
    public void unfollow(@PathVariable int targetId, Authentication auth) {
        friendsService.unfollow(PseudoUser.of(auth.getName()), targetId);
    }

    @GetMapping("/api/friends")
    public Friendships getFriendships(Authentication auth) {
        return friendsService.getFriendships(PseudoUser.of(auth.getName()));
    }
}
