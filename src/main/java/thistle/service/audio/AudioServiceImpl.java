package thistle.service.audio;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import thistle.Properties;
import thistle.domain.Audio;
import thistle.domain.User;
import thistle.exception.ThistleException;
import thistle.repository.AudioRepository;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.util.DigestUtils.md5DigestAsHex;

@Slf4j
@Service
@RequiredArgsConstructor
public class AudioServiceImpl implements AudioService {

    private final Properties properties;
    private final AudioRepository audioRepository;

    @Override
    @SneakyThrows
    public void upload(User user, MultipartFile file, String name) {
        log.info("Uploading file, type={}", file.getContentType());
        String md5 = md5DigestAsHex(file.getBytes());
        Optional<Audio> audio = audioRepository.findByOwnerAndMd5(user, md5);
        if (audio.isPresent()) {
            String message = String.format("You already have such record named '%s' in your list", audio.get().getName());
            throw new ThistleException(message);
        }
        Files.createDirectories(Paths.get(properties.getStorage()));
        file.transferTo(Paths.get(properties.getStorage(), md5));
        audioRepository.save(new Audio(user, name, md5));
    }

    @Override
    public List<UserAudio> getAudios(User user, int pageIndex, int pageSize) {
        Sort sort = Sort.by("id").descending();
        List<Audio> audios = audioRepository.findAllByOwner(user, PageRequest.of(pageIndex, pageSize, sort)).getContent();
        return audios.stream()
                .map(UserAudio::of)
                .collect(Collectors.toList());
    }
}
