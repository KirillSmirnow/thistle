package thistle.service.user;

import thistle.domain.User;

public interface UserService {

    AccessToken vkAuth(VkAuth vkAuth);

    Profile getProfile(User user);
}
