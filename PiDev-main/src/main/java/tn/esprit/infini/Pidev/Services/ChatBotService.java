package tn.esprit.infini.Pidev.Services;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.infini.Pidev.Repository.ChatBotRepository;
import tn.esprit.infini.Pidev.entities.ChatBot;

@Service
@AllArgsConstructor
public class ChatBotService {
    @Autowired
    ChatBotRepository chatBotRepository;
    public String processMessage(String message) {
        String response = generateResponse(preprocessMessage(message));
        chatBotRepository.save(new ChatBot(message, response));
        return response;
    }

    private String preprocessMessage(String message) {

        return message.replaceAll("[^a-zA-Z0-9]", "").toLowerCase();
    }

    private String generateResponse(String message) {
        switch (message) {
            case "hello":
            case "hi":
                return "Hi there, can we help you?";
            case "help":
            case "ask":
            case "question":
                return "If you have a question, don't hesitate to ask!";
            case "pack":
            case "price":
                return "Check the list of our packs. We have many types that you may like!";
            case "credit":
                return "Please fill the credit's form to know more details.";
            case "insurance":
                return "Please fill the insurance's from to know more details!";
            case "bill":
            case "fine":
                return "If you have a fine or bill you want to pay, check the fine and bill section!";
            case "complaint":
                return "If you have any complaint, please fill the complaint's form and wait for our response!";
            default:
                return "I'm sorry, I don't understand. Can you please send you phone number we will call you later.";
        }

    }
}
