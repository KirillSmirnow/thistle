package thistle.service.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import thistle.domain.Vk;

@Getter
@RequiredArgsConstructor
public class VkDto {

    private final String firstName;
    private final String lastName;
    private final String photo;

    public static VkDto of(Vk vk) {
        return new VkDto(vk.getFirstName(), vk.getLastName(), vk.getPhoto());
    }
}
