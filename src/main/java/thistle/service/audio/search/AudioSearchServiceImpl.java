package thistle.service.audio.search;

import lombok.RequiredArgsConstructor;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.QueryBuilder;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import thistle.domain.Audio;
import thistle.domain.AudioIndex;
import thistle.domain.User;
import thistle.repository.AudioIndexRepository;
import thistle.service.audio.UserAudio;

import java.util.List;
import java.util.stream.Collectors;

import static org.elasticsearch.index.query.QueryBuilders.*;

@Service
@RequiredArgsConstructor
public class AudioSearchServiceImpl implements AudioSearchService {

    private final AudioIndexRepository audioIndexRepository;

    @Override
    public void index(Audio audio) {
        audioIndexRepository.save(AudioIndex.of(audio));
    }

    @Override
    public void remove(Audio audio) {
        audioIndexRepository.delete(AudioIndex.of(audio));
    }

    @Override
    public AudioSearchResult search(User user, String query, int pageIndex, int pageSize) {
        Pageable pageable = PageRequest.of(pageIndex, pageSize);
        QueryBuilder beCurrentUser = termQuery("userId", user.getId());
        QueryBuilder matchExactly = matchPhraseQuery("name", query);
        QueryBuilder matchLoosely = matchQuery("name", query)
                .prefixLength(3)
                .fuzziness(Fuzziness.AUTO)
                .analyzer("english")
                .analyzer("russian");
        QueryBuilder searchMine = boolQuery()
                .must(beCurrentUser)
                .should(matchExactly)
                .should(matchLoosely)
                .minimumShouldMatch(1);
        QueryBuilder searchOthers = boolQuery()
                .mustNot(beCurrentUser)
                .should(matchExactly)
                .should(matchLoosely)
                .minimumShouldMatch(1);
        List<UserAudio> mine = audioIndexRepository.search(searchMine, pageable).getContent().stream()
                .map(UserAudio::of)
                .collect(Collectors.toList());
        List<UserAudio> others = audioIndexRepository.search(searchOthers, pageable).getContent().stream()
                .map(UserAudio::of)
                .collect(Collectors.toList());
        return new AudioSearchResult(mine, others);
    }
}
