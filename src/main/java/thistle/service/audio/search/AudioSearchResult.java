package thistle.service.audio.search;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import thistle.service.audio.UserAudio;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class AudioSearchResult {

    private final List<UserAudio> mine;
    private final List<UserAudio> others;
}
