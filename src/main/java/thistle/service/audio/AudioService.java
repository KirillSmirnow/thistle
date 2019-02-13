package thistle.service.audio;

import org.springframework.web.multipart.MultipartFile;
import thistle.domain.User;

public interface AudioService {

    void upload(User user, MultipartFile file, String name);
}
