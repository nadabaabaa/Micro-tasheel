package tn.esprit.infini.Pidev.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import tn.esprit.infini.Pidev.entities.Account;
import jakarta.persistence.*;
import tn.esprit.infini.Pidev.entities.Invest;
import tn.esprit.infini.Pidev.entities.Transaction;
import tn.esprit.infini.Pidev.entities.User;

import java.util.List;


public interface AccountRepository extends JpaRepository<Account, Integer> {
    Account getAccountByUser(User u);
}
