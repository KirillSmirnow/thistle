package thistle.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import thistle.security.PseudoUser;
import thistle.service.chat.ChatCreate;
import thistle.service.chat.ChatDto;
import thistle.service.chat.ChatService;
import thistle.service.chat.Message;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @PostMapping("/api/chats")
    public void create(@RequestBody ChatCreate chatCreate, Authentication auth) {
        chatService.create(PseudoUser.of(auth.getName()), chatCreate);
    }

    @GetMapping("/api/chats")
    public List<ChatDto> getChats(Authentication auth) {
        return chatService.getChats(PseudoUser.of(auth.getName()));
    }

    @PostMapping("/api/chats/{chatId}/messages")
    public void sendMessage(@PathVariable UUID chatId, @RequestParam String text, Authentication auth) {
        chatService.sendMessage(PseudoUser.of(auth.getName()), chatId, text);
    }

    @GetMapping("/api/chats/{chatId}/messages")
    public List<Message> getMessages(@PathVariable UUID chatId,
                                     @RequestParam(defaultValue = "0") int pageIndex,
                                     @RequestParam(defaultValue = "1000") int pageSize,
                                     Authentication auth) {
        return chatService.getMessages(PseudoUser.of(auth.getName()), chatId, pageIndex, pageSize);
    }
}
