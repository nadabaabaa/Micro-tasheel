package tn.esprit.infini.Pidev.entities;

import jakarta.persistence.*;
import lombok.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Account implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private Date creationDate;
    private float balance;
    @OneToOne
    @JoinColumn(name = "user_id")

    private User user;
    @ManyToOne
    @JoinColumn(name = "transaction_id")

    private Transaction transaction;

}
