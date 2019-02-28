package thistle.service.files;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import thistle.Properties;

import java.nio.file.Path;
import java.nio.file.Paths;

@TestConfiguration
@ComponentScan
public class FilesTestConfig {

    @Bean
    public Properties properties() {
        Path tmpDir = Paths.get(System.getProperty("java.io.tmpdir"));
        Properties properties = new Properties();
        properties.setStorage(tmpDir.toString());
        return properties;
    }
}
