package tn.esprit.infini.Pidev.Auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tn.esprit.infini.Pidev.Config.JwtService;
import tn.esprit.infini.Pidev.Repository.UserRepository;
import tn.esprit.infini.Pidev.entities.TypeUser;
import tn.esprit.infini.Pidev.entities.User;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository UR;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    public AuthenticationResponse register(User u) {
        /*User user= User.builder() //user should be var
                .firstName(u.getFirstName())
                .lastName(u.getLastName())
                .creationDate(Date.valueOf(LocalDate.now()))
                .email(u.getEmail())//might cause problem
                .password(passwordEncoder.encode(u.getPassword()))
                .type(TypeUser.Casual_Client)
                .build();*/
        u.setPassword(passwordEncoder.encode(u.getPassword()));
        u.setLastBanDate(null);
        u.setNombreTentatives(0);
        u.setType(TypeUser.Casual_Client);
        u.setCreationDate(Date.valueOf(LocalDate.now()));
        UR.save(u/*ser*/);
        String jwtToken=jwtService.generateToken(u/*ser*/); //string should be var
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),request.getPassword()
                )
        );
        Optional<User> user=UR.findByEmail(request.getEmail()); //optional should be var
        String jwtToken=jwtService.generateToken(user.get()); //string should be var
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();


    }
}
