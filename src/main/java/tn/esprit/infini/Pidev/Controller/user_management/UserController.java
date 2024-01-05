package tn.esprit.infini.Pidev.Controller.user_management;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
import tn.esprit.infini.Pidev.Service.user_management.IUserService;
import tn.esprit.infini.Pidev.Service.user_management.UserDetailsImpl;
import tn.esprit.infini.Pidev.Service.user_management.exception.BadRequestException;
import tn.esprit.infini.Pidev.entities.User;
import tn.esprit.infini.Pidev.security.JwtUtils;


import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*")

@RequestMapping("/users")
@RestController

public class UserController {

    @Autowired
    private IUserService iUserService ;

    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = iUserService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/get-user-by-id/{id}")
    public ResponseEntity<User> getUserById(@PathVariable int id) {
        User user = iUserService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/get-user-by-username/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
        User user = iUserService.getUserByUsername(username);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/delete-user-by-username/{username}")
    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> deleteUserByUsername(@PathVariable String username) {
        iUserService.deleteUserByUsername(username);
        return ResponseEntity.ok("User deleted");
    }

    @DeleteMapping(value = "/delete-user-by-id/{id}")
   // @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> delete(@PathVariable("id") int id) {
        iUserService.deleteUserById(id);
        return ResponseEntity.ok("User deleted");
    }

}

























//
//
//
//@RequestMapping("/users")
//@RequiredArgsConstructor
//@RestController
//public class HomeController {
//        @Autowired
//        private UserImpl userService;
//        private ModelMapper modelMapper;
//
////        @PostMapping("/signin")
////
////        public String login(//
////                            @PathVariable("Username") @RequestParam String username, //
////                            @PathVariable("Password") @RequestParam String password) {
////            return userService.signin(username, password);
////        }
//@PostMapping("/registration")
//public String createNewUser(@RequestBody User user) {
//    String msg = "";
//    User userExists = userService.findUserByUserName(user.getUsername());
//    if (userExists != null) {
//        msg = "There is already a user registered with the user name provided";
//    } else {
//        userService.saveUser(user);
//        msg = "OK";
//    }
//    return msg;
//}
//

//
//        @GetMapping(value = "/{username}")
//        @PreAuthorize("hasRole('ROLE_ADMIN')")
//        public UserResponseDTO search(@PathVariable("Username") String username) {
//            return modelMapper.map(userService.search(username), UserResponseDTO.class);
//        }
//
//        @GetMapping(value = "/me")
//        @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
//        public UserResponseDTO whoami(HttpServletRequest req) {
//            return modelMapper.map(userService.whoami(req), UserResponseDTO.class);
//        }
//
//        @GetMapping("/refresh")
//        @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
//        public String refresh(HttpServletRequest req) {
//            return userService.refresh(req.getRemoteUser());
//        }
//
//    }
//
////    @Autowired
////    IUserService userService;
////
////
////        @GetMapping("/{userId}")
////        public UserDTO getUserById(@PathVariable int userId) {
////            return userService.getUserById(userId);
////        }
////        @PutMapping("/{userId}")
////        public UserDTO updateUser(@PathVariable int userId, @RequestBody UserDTO userDTO) {
////            return userService.updateUser(userId, userDTO);
////        }
////        @DeleteMapping("/delete/{userId}")
////        public void deleteUser(@PathVariable int userId) {
////            userService.deleteUser(userId);
////        }
////    }
