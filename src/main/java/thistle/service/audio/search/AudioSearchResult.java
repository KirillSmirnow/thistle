package thistle.service.audio.search;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import thistle.service.audio.UserAudio;

import java.util.List;

import static java.util.Collections.emptyList;

@Getter
@RequiredArgsConstructor
public class AudioSearchResult {

    private final List<UserAudio> mine;
    private final List<UserAudio> others;

    public static AudioSearchResult empty() {
        return new AudioSearchResult(emptyList(), emptyList());
    }
}
