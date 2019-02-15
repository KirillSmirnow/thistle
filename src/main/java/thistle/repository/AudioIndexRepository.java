package thistle.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import thistle.domain.AudioIndex;

public interface AudioIndexRepository extends ElasticsearchRepository<AudioIndex, Integer> {
}
