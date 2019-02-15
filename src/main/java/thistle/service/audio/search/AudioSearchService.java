package thistle.service.audio.search;

import thistle.domain.Audio;
import thistle.domain.User;

public interface AudioSearchService {

    void index(Audio audio);

    void remove(Audio audio);

    AudioSearchResult search(User user, String query, int pageIndex, int pageSize);
}
