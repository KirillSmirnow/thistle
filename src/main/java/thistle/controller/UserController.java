package thistle.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import thistle.security.BearerAuthentication;
import thistle.service.user.AccessToken;
import thistle.service.user.Profile;
import thistle.service.user.UserService;
import thistle.service.user.VkAuth;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/auth/vk")
    public AccessToken vkAuth(@RequestBody VkAuth vkAuth) {
        return userService.vkAuth(vkAuth);
    }

    @GetMapping("/api/profile")
    public Profile getProfile(BearerAuthentication auth) {
        return userService.getProfile(auth.getPrincipal());
    }
}
