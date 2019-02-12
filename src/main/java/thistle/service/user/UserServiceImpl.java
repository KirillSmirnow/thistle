package thistle.service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import thistle.Properties;
import thistle.domain.User;
import thistle.domain.Vk;
import thistle.exception.ThistleException;
import thistle.repository.UserRepository;
import thistle.repository.VkRepository;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final Properties properties;
    private final UserRepository userRepository;
    private final VkRepository vkRepository;

    @Override
    public AccessToken vkAuth(VkAuth vkAuth) {
        if (vkAuth.isGenuine(properties.getVk().getAppId(), properties.getVk().getSecretKey())) {
            throw new ThistleException("Not authenticated");
        }
        Vk vk = vkRepository.findById(vkAuth.getVkId()).orElseGet(() -> createUser(vkAuth));
        User user = vk.getUser();
        return new AccessToken(user.updateAccessToken());
    }

    @Override
    public Profile getProfile(User user) {
        return Profile.of(user);
    }

    private Vk createUser(VkAuth vkAuth) {
        User user = userRepository.save(new User(vkAuth.getFirstName(), vkAuth.getLastName()));
        return vkRepository.save(new Vk(vkAuth.getVkId(), user, vkAuth.getFirstName(), vkAuth.getLastName(), vkAuth.getPhoto()));
    }
}
