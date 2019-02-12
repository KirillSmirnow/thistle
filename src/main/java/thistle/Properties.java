package thistle;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties("thistle")
public class Properties {

    private String origin;
    private Vk vk = new Vk();

    @Data
    public static class Vk {

        private String appId;
        private String secretKey;
    }
}
