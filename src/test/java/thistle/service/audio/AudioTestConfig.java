package thistle.service.audio;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import thistle.repository.AudioIndexRepository;
import thistle.service.files.FilesService;

@TestConfiguration
@ComponentScan
@MockBean({AudioIndexRepository.class, FilesService.class})
public class AudioTestConfig {
}
