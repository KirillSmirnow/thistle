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
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import static org.springframework.util.DigestUtils.md5DigestAsHex;

@Slf4j
@Service
@RequiredArgsConstructor
public class AudioServiceImpl implements AudioService {

    private final Properties properties;
    private final AudioRepository audioRepository;

    private static String eraseFileType(String filename) {
        if (filename == null) return null;
        int lastDotIndex = filename.lastIndexOf('.');
        if (lastDotIndex > 0) {
            return filename.substring(0, lastDotIndex);
        }
        return filename;
    }

    @Override
    public void upload(User user, MultipartFile file, String name) {
        log.info("Uploading {name={}, size={}, type={}}", file.getOriginalFilename(), file.getSize(), file.getContentType());
        if (file.getContentType() != null && file.getContentType().startsWith("audio/")) {
            uploadSingleFile(user, file, name);
        } else if (file.getContentType() != null && file.getContentType().equals("application/zip")) {
            uploadArchive(user, file);
        } else {
            throw new ThistleException("Wrong audio format");
        }
    }

    @Override
    public void delete(User user, int audioId) {
        Audio audio = audioRepository.findById(audioId)
                .orElseThrow(() -> new ThistleException("Audio not found"));
        if (!audio.getOwner().equals(user)) {
            throw new ThistleException("Permission denied");
        }
        audioRepository.delete(audio);
    }

    @Override
    public List<UserAudio> getAudios(User user, int pageIndex, int pageSize) {
        Sort sort = Sort.by("id").descending();
        List<Audio> audios = audioRepository.findAllByOwner(user, PageRequest.of(pageIndex, pageSize, sort)).getContent();
        return audios.stream()
                .map(UserAudio::of)
                .collect(Collectors.toList());
    }

    @SneakyThrows
    private void uploadSingleFile(User user, MultipartFile file, String name) {
        String md5 = md5DigestAsHex(file.getBytes());
        Optional<Audio> audio = audioRepository.findByOwnerAndMd5(user, md5);
        if (audio.isPresent()) {
            String message = String.format("You already have such record named '%s' in your list", audio.get().getName());
            throw new ThistleException(message);
        }
        if (name == null || name.isEmpty()) {
            name = eraseFileType(file.getOriginalFilename());
        }
        Files.createDirectories(Paths.get(properties.getStorage()));
        file.transferTo(Paths.get(properties.getStorage(), md5));
        audioRepository.save(new Audio(user, name, md5));
    }

    @SneakyThrows
    private void uploadArchive(User user, MultipartFile archive) {
        Path path = Files.createTempFile("thistle-", ".tmp");
        archive.transferTo(path);
        ZipFile zip = new ZipFile(path.toFile());
        zip.stream().forEach(entry -> processZipEntry(user, zip, entry));
    }

    @SneakyThrows
    private void processZipEntry(User user, ZipFile zip, ZipEntry entry) {
        if (entry.isDirectory()) return;
        String md5 = md5DigestAsHex(zip.getInputStream(entry));
        if (!audioRepository.findByOwnerAndMd5(user, md5).isPresent()) {
            String name = eraseFileType(entry.getName());
            Files.createDirectories(Paths.get(properties.getStorage()));
            Files.copy(zip.getInputStream(entry), Paths.get(properties.getStorage(), md5));
            audioRepository.save(new Audio(user, name, md5));
        }
    }
}
