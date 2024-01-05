package tn.esprit.infini.Pidev.Controller.user_management;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;


import tn.esprit.infini.Pidev.Controller.user_management.request.LoginRequest;
import tn.esprit.infini.Pidev.Controller.user_management.request.SignupRequest;
import tn.esprit.infini.Pidev.Controller.user_management.response.JwtResponse;
import tn.esprit.infini.Pidev.Controller.user_management.response.MessageResponse;
import tn.esprit.infini.Pidev.Repository.IRoleRepo;
import tn.esprit.infini.Pidev.Repository.IUserRepo;
import tn.esprit.infini.Pidev.Service.user_management.UserDetailsImpl;
import tn.esprit.infini.Pidev.Service.user_management.exception.BadRequestException;
import tn.esprit.infini.Pidev.entities.User;
import tn.esprit.infini.Pidev.security.JwtUtils;


import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/test")
public class TestController {

    @Autowired
    private IUserRepo iUserRepo ;

    @GetMapping("/all")
    public String allAccess() {
        return "Public Content.";
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public String userAccess() {
        return "User Content.";
    }


    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminAccess() {
        return "Admin Board.";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete-user/{id}")
    public void deleteUser(@PathVariable("id") int id){
        iUserRepo.deleteById(id);
    }
}