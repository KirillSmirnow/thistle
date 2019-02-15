package thistle.service.audio;

import lombok.Getter;
import thistle.domain.Audio;

@Getter
public class UserAudio {

    private final int id;
    private final String name;
    private final String url;

    private UserAudio(int id, String name, String md5) {
        this.id = id;
        this.name = name;
        this.url = String.format("/music/%s", md5);
    }

    public static UserAudio of(Audio audio) {
        return new UserAudio(audio.getId(), audio.getName(), audio.getMd5());
    }
}
