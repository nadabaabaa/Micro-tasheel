package tn.esprit.infini.Pidev.Services;

import tn.esprit.infini.Pidev.entities.Email;

public interface IEmailService {
    public String sendEmail(Email details);
}
