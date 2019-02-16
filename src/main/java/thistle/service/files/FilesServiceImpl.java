package thistle.service.files;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import thistle.Properties;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

@Slf4j
@Service
@RequiredArgsConstructor
public class FilesServiceImpl implements FilesService {

    private final Properties properties;

    @Override
    public void saveFile(InputStream inputStream, String md5) {
        Path directoryPath = Paths.get(properties.getStorage());
        Path filePath = Paths.get(properties.getStorage(), md5);
        if (Files.notExists(filePath)) {
            try {
                Files.createDirectories(directoryPath);
                Files.copy(inputStream, filePath, REPLACE_EXISTING);
            } catch (Exception e) {
                log.warn("Unable to save file: {}", e.getMessage());
            }
        }
    }
}
