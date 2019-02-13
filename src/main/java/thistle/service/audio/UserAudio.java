package thistle.service.audio;

import lombok.Getter;
import thistle.domain.Audio;

@Getter
public class UserAudio {

    private final String name;
    private final String url;

    private UserAudio(String name, String md5) {
        this.name = name;
        this.url = String.format("/music/%s", md5);
    }

    public static UserAudio of(Audio audio) {
        return new UserAudio(audio.getName(), audio.getMd5());
    }
}
