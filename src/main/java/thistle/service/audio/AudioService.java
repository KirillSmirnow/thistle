package thistle.service.audio;

import org.springframework.web.multipart.MultipartFile;
import thistle.domain.User;

import java.util.List;

public interface AudioService {

    void upload(User user, MultipartFile file, String name);

    List<UserAudio> getAudios(User user, int pageIndex, int pageSize);
}
