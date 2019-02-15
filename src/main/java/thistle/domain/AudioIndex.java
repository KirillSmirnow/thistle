package thistle.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "audios", shards = 1, replicas = 0)
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class AudioIndex {

    private int id;
    private int userId;
    private String name;
    private String md5;

    public static AudioIndex of(Audio audio) {
        return new AudioIndex(audio.getId(), audio.getOwner().getId(), audio.getName(), audio.getMd5());
    }
}
