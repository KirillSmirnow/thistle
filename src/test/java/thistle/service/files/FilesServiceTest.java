package thistle.service.files;

import lombok.SneakyThrows;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import thistle.Properties;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.util.DigestUtils.md5DigestAsHex;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = FilesTestConfig.class)
public class FilesServiceTest {

    @Autowired
    private FilesService filesService;
    @Autowired
    private Properties properties;

    private byte[] content;
    private String md5;

    @Before
    public void prepareContent() {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        int size = random.nextInt(32, 2048);
        content = new byte[size];
        random.nextBytes(content);
        md5 = md5DigestAsHex(content);
    }

    @Test
    @SneakyThrows
    public void whenSaveFile_thenFileExistsAndSameContent() {
        InputStream inputStream = new ByteArrayInputStream(content);
        filesService.saveFile(inputStream, md5);

        Path path = Paths.get(properties.getStorage(), md5);
        assertTrue(Files.exists(path));
        assertArrayEquals(content, Files.readAllBytes(path));
    }

    @After
    @SneakyThrows
    public void clean() {
        Files.deleteIfExists(Paths.get(properties.getStorage(), md5));
    }
}
