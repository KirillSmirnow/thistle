package thistle.service.audio;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import thistle.domain.Audio;
import thistle.domain.User;
import thistle.exception.ThistleException;
import thistle.repository.AudioRepository;
import thistle.repository.UserRepository;

import static org.junit.Assert.fail;

@RunWith(SpringRunner.class)
@DataJpaTest
@ContextConfiguration(classes = AudioTestConfig.class)
public class AudioServiceTest {

    @Autowired
    private AudioService audioService;
    @Autowired
    private AudioRepository audioRepository;
    @Autowired
    private UserRepository userRepository;

    private User hacker;
    private User audioOwner;
    private Audio audio;

    @Before
    public void init() {
        hacker = userRepository.save(new User("Hacker", "Hacker"));
        audioOwner = userRepository.save(new User("AudioOwner", "AudioOwner"));
        audio = audioRepository.save(new Audio(audioOwner, "Audio", "md5"));
    }

    @Test
    public void whenDeleteUnauthorizedAudio_thenThrowException() {
        try {
            audioService.delete(hacker, audio.getId());
            fail();
        } catch (ThistleException e) {
        }
    }
}
