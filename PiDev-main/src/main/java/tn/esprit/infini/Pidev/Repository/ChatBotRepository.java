package tn.esprit.infini.Pidev.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.infini.Pidev.entities.ChatBot;

public interface ChatBotRepository extends JpaRepository<ChatBot, Long> {
}