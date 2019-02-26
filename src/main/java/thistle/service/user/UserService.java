package thistle.service.user;

import thistle.domain.User;

public interface UserService {

    String vkAuth(VkAuth vkAuth);

    Profile getProfile(User user);
}
