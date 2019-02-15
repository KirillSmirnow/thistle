package thistle.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import thistle.security.BearerAuthentication;
import thistle.service.audio.AudioService;
import thistle.service.audio.UserAudio;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AudioController {

    private final AudioService audioService;

    @PostMapping("/api/audios/upload")
    public void upload(@RequestParam(required = true) MultipartFile file,
                       @RequestParam(required = false) String name,
                       BearerAuthentication auth) {
        audioService.upload(auth.getPrincipal(), file, name);
    }

    @DeleteMapping("/api/audios/{id}")
    public void delete(@PathVariable int id, BearerAuthentication auth) {
        audioService.delete(auth.getPrincipal(), id);
    }

    @GetMapping("/api/audios")
    public List<UserAudio> getAudios(@RequestParam(defaultValue = "0") int pageIndex,
                                     @RequestParam(defaultValue = "1000") int pageSize,
                                     BearerAuthentication auth) {
        return audioService.getAudios(auth.getPrincipal(), pageIndex, pageSize);
    }
}
