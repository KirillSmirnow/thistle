package thistle.service.user;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import thistle.Properties;
import thistle.exception.ThistleException;
import thistle.repository.VkRepository;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.Assert.*;
import static org.springframework.util.DigestUtils.md5DigestAsHex;

@RunWith(SpringRunner.class)
@DataJpaTest
@ContextConfiguration(classes = UserTestConfig.class)
public class UserServiceTest {

    @Autowired
    private UserService userService;
    @Autowired
    private Properties properties;
    @Autowired
    private VkRepository vkRepository;

    private static String md5(String raw) {
        return md5DigestAsHex(raw.getBytes());
    }

    @Test
    public void whenGenuineUserSignUp_thenUserExists() {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        long vkId = random.nextLong();
        String toHash = properties.getVk().getAppId() + vkId + properties.getVk().getSecretKey();
        VkAuth vkAuth = new VkAuth(vkId, md5(toHash), "First Name", "Last name", "Photo");
        userService.vkAuth(vkAuth);
        assertTrue(vkRepository.existsById(vkId));
    }

    @Test
    public void whenNotGenuineUserSignUp_thenThrowExceptionAndUserNotExists() {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        long vkId = random.nextLong();
        String toHash = properties.getVk().getAppId() + vkId + UUID.randomUUID();
        VkAuth vkAuth = new VkAuth(vkId, md5(toHash), "First Name", "Last name", "Photo");
        try {
            userService.vkAuth(vkAuth);
            fail();
        } catch (ThistleException e) {
            assertFalse(vkRepository.existsById(vkId));
        }
    }
}
