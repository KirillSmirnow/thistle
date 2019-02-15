package thistle.service.audio.search;

import lombok.RequiredArgsConstructor;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
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
        QueryBuilder beCurrentUser = QueryBuilders.termQuery("userId", user.getId());
        QueryBuilder matchPhrase = QueryBuilders.matchQuery("name", query)
                .prefixLength(3)
                .fuzziness(Fuzziness.AUTO)
                .analyzer("english")
                .analyzer("russian");
        QueryBuilder searchMine = QueryBuilders.boolQuery()
                .must(beCurrentUser)
                .must(matchPhrase);
        QueryBuilder searchOthers = QueryBuilders.boolQuery()
                .mustNot(beCurrentUser)
                .must(matchPhrase);
        List<UserAudio> mine = audioIndexRepository.search(searchMine, pageable).getContent().stream()
                .map(UserAudio::of)
                .collect(Collectors.toList());
        List<UserAudio> others = audioIndexRepository.search(searchOthers, pageable).getContent().stream()
                .map(UserAudio::of)
                .collect(Collectors.toList());
        return new AudioSearchResult(mine, others);
    }
}
