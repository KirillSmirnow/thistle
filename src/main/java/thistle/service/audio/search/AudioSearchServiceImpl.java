package thistle.service.audio.search;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import thistle.domain.Audio;
import thistle.domain.AudioIndex;
import thistle.repository.AudioIndexRepository;

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
}
