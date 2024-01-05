package tn.esprit.infini.Pidev.Services.user_management;


import tn.esprit.infini.Pidev.entities.User;

import java.util.List;
import java.util.Optional;

public interface IUserService {

   // String signin(String username, String password);

//    String signup(User user);
//
//    void delete(String username);
//
//    User search(String username);
//
//    User whoami(HttpServletRequest req);
//
//    String refresh(String username);
//
//    User findUserByUserName(String userName);
//
//    User saveUser(User user);

    Optional<User> findByEmail(String email);

    User createUser(User user);


    List<User> getAllUsers();

    User getUserById(int id);

    User getUserByUsername(String username);

    void deleteUserById(int id);

    void deleteUserByUsername(String username);
}
