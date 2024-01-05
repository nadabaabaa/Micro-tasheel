package tn.esprit.infini.Pidev.Controller.user_management;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;


import tn.esprit.infini.Highteck.Service.user_management.AuthService;
import tn.esprit.infini.Pidev.Controller.user_management.request.ForgotPasswordRequest;
import tn.esprit.infini.Pidev.Controller.user_management.request.LoginRequest;
import tn.esprit.infini.Pidev.Controller.user_management.request.ResetPasswordRequest;
import tn.esprit.infini.Pidev.Controller.user_management.request.SignupRequest;
import tn.esprit.infini.Pidev.Controller.user_management.response.JwtResponse;
import tn.esprit.infini.Pidev.Controller.user_management.response.MessageResponse;
import tn.esprit.infini.Pidev.Repository.IRoleRepo;
import tn.esprit.infini.Pidev.Repository.IUserRepo;
import tn.esprit.infini.Pidev.Service.user_management.*;
import tn.esprit.infini.Pidev.Service.user_management.exception.BadRequestException;
import tn.esprit.infini.Pidev.entities.PasswordResetToken;
import tn.esprit.infini.Pidev.entities.User;
import tn.esprit.infini.Pidev.security.JwtUtils;


import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
public class ForgotPasswordController {

    @Autowired
    private IUserService iUserService;

    @Autowired
    private final PasswordResetTokenService passwordResetTokenService;

    @Autowired
    private final EmailService emailService;

    public ForgotPasswordController(UserService userService,
                                    PasswordResetTokenService passwordResetTokenService,
                                    EmailService emailService) {
        this.iUserService = userService;
        this.passwordResetTokenService = passwordResetTokenService;
        this.emailService = emailService;
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestBody ForgotPasswordRequest request) {
        String email = request.getEmail();
        User user = iUserService.findByEmail(email).get();
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        PasswordResetToken token = passwordResetTokenService.generateTokenForUser(user);
        emailService.sendPasswordResetEmail(email, token);

        return ResponseEntity.ok("Password reset email sent");
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody ResetPasswordRequest request) {
        String tokenString = request.getToken();
        PasswordResetToken token = passwordResetTokenService.findByToken(tokenString);
        if (token == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid or expired token");
        }

        Date expiryDate = token.getExpiryDate();
        if (expiryDate.before(new Date())) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid or expired token");
        }

        User user = token.getUser();
        String newPassword = request.getNewPassword();
        user.setPassword(newPassword);
        iUserService.createUser(user);

        passwordResetTokenService.delete(token);

        return ResponseEntity.ok("Password reset successful");
    }
}
