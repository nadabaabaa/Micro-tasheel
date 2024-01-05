package tn.esprit.infini.Pidev.entities;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.*;

import javax.persistence.*;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;



import java.io.Serializable;
import java.util.Date;

@Data
@Builder
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "username"),
                @UniqueConstraint(columnNames = "email")
        })
public class User implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
            private int id;
            private String username;
            private String firstName ;
            private String lastName;
            private String email;


            private String password;
            private double phoneNumber;

            @Enumerated(value = EnumType.STRING)
            private Gender gender;

        @ManyToMany(fetch = FetchType.LAZY)
        @JoinTable(	name = "user_roles",
                joinColumns = @JoinColumn(name = "user_id"),
                inverseJoinColumns = @JoinColumn(name = "role_id"))
        private Set<Role> roles = new HashSet<>();
        @Column(name = "email_verified")
        private boolean emailVerified;
        @Temporal(TemporalType.DATE)
        private Date creationDate;

        @Temporal(TemporalType.DATE)
        private Date updateDate;
        @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)

        private Account account;
     public User(String username, String email,String firstName, String lastName,Gender gender, double phoneNumber,String encode){
        this.username = username;
        this.email = email;
        this.firstName = firstName ;
        this.lastName = lastName;
        this.gender = gender;
        this.phoneNumber = phoneNumber ;
        this.password = encode;
        this.account = new Account(this);


     }
    public void setAccount(Account account) {
        this.account = account;
        account.setUser(this);
    }


}
