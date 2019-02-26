package thistle;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties("thistle")
public class Properties {

    private Vk vk = new Vk();
    private String storage;
    private String jwtSigningKey;

    @Data
    public static class Vk {

        private String appId;
        private String secretKey;
    }
}
