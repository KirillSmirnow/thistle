package thistle.service.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import static org.springframework.util.DigestUtils.md5DigestAsHex;

@Getter
@RequiredArgsConstructor
public class VkAuth {

    private final long vkId;
    private final String hash;
    private final String firstName;
    private final String lastName;
    private final String photo;

    public boolean isGenuine(String appId, String secretKey) {
        return md5DigestAsHex((appId + vkId + secretKey).getBytes()).equals(hash);
    }
}
