package thistle.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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
    public void upload(@RequestParam MultipartFile file, @RequestParam String name, BearerAuthentication auth) {
        audioService.upload(auth.getPrincipal(), file, name);
    }

    @GetMapping("/api/audios")
    public List<UserAudio> getAudios(@RequestParam(defaultValue = "0") int pageIndex,
                                     @RequestParam(defaultValue = "100") int pageSize,
                                     BearerAuthentication auth) {
        return audioService.getAudios(auth.getPrincipal(), pageIndex, pageSize);
    }
}
