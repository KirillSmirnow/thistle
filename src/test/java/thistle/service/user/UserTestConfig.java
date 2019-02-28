package thistle.service.user;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import thistle.Properties;
import thistle.security.UserInMemoryAuthorizationCodeServices;

import java.util.UUID;

@TestConfiguration
@ComponentScan
@MockBean(UserInMemoryAuthorizationCodeServices.class)
public class UserTestConfig {

    @Bean
    public Properties properties() {
        Properties.Vk vkProperties = new Properties.Vk();
        vkProperties.setAppId(UUID.randomUUID().toString());
        vkProperties.setSecretKey(UUID.randomUUID().toString());

        Properties properties = new Properties();
        properties.setVk(vkProperties);
        return properties;
    }
}
