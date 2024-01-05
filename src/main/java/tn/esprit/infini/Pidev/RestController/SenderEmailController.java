package tn.esprit.infini.Pidev.RestController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import tn.esprit.infini.Pidev.Services.EmailService;
import tn.esprit.infini.Pidev.entities.Email;
@RestController
public class SenderEmailController {

    @Autowired
    private EmailService service;


    // @PostMapping  ("/sendemail") //oubien get ssss

    @PostMapping("/sendMail")
    public String
    sendMail(@RequestBody Email details) {
        String status
                = service.sendEmail(details);

        return status;
    }
}