package thistle.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import thistle.security.PseudoUser;
import thistle.service.audio.AudioService;
import thistle.service.audio.UserAudio;
import thistle.service.audio.search.AudioSearchResult;
import thistle.service.audio.search.AudioSearchService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AudioController {

    private final AudioService audioService;
    private final AudioSearchService audioSearchService;

    @PostMapping("/api/audios/upload")
    public void upload(@RequestParam(required = true) MultipartFile file,
                       @RequestParam(required = false) String name,
                       Authentication auth) {
        audioService.upload(PseudoUser.of(auth.getName()), file, name);
    }

    @DeleteMapping("/api/audios/{id}")
    public void delete(@PathVariable int id, Authentication auth) {
        audioService.delete(PseudoUser.of(auth.getName()), id);
    }

    @GetMapping("/api/audios")
    public List<UserAudio> getAudios(@RequestParam(defaultValue = "0") int pageIndex,
                                     @RequestParam(defaultValue = "1000") int pageSize,
                                     Authentication auth) {
        return audioService.getAudios(PseudoUser.of(auth.getName()), pageIndex, pageSize);
    }

    @PostMapping("/api/audios/search")
    public AudioSearchResult search(@RequestParam(defaultValue = "") String query,
                                    @RequestParam(defaultValue = "0") int pageIndex,
                                    @RequestParam(defaultValue = "1000") int pageSize,
                                    Authentication auth) {
        return audioSearchService.search(PseudoUser.of(auth.getName()), query, pageIndex, pageSize);
    }
}
