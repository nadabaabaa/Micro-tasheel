package tn.esprit.infini.Pidev.RestController;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tn.esprit.infini.Pidev.Services.ChatBotService;

@RestController
@AllArgsConstructor
@RequestMapping("/ChatBot")
public class ChatBotController {

    @Autowired
    ChatBotService chatBotService;

    @PostMapping("/chat")
    public ResponseEntity<String> processMessage(@RequestBody String message) {
        String response = chatBotService.processMessage(message);
        return ResponseEntity.ok(response);
    }
}