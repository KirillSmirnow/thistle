package thistle.service.audio.search;

import thistle.domain.Audio;

public interface AudioSearchService {

    void index(Audio audio);

    void remove(Audio audio);
}
