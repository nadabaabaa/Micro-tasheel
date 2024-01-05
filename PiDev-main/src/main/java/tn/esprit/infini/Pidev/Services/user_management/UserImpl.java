package tn.esprit.infini.Pidev.Services.user_management;//package com.group3.camping_project.service.user_management;
//
//import com.group3.camping_project.entities.User;
//import com.group3.camping_project.repository.IUserRepo;
//import com.group3.camping_project.service.user_management.exception.CustomException;
//import com.group3.camping_project.service.user_management.exception.EmailAlreadyExistsException;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//
//import javax.servlet.http.HttpServletRequest;
//import java.util.Date;
//
//
//@RequiredArgsConstructor
//@Service
//public class UserImpl implements IUserService {
//
//    @Autowired
//    private IUserRepo userRepository;
//
//    //@Autowired
//    private PasswordEncoder passwordEncoder;
//
//    //@Autowired
//
//
//    //@Autowired
//    private AuthenticationManager authenticationManager;
//
//    //    @Override
////    public String signin(String username, String password) {
////        try {
////            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
////            return jwtTokenProvider.createToken(username, userRepository.findByUsername(username).getRoleNames());
////        } catch (AuthenticationException e) {
////            throw new CustomException("Invalid username/password supplied", HttpStatus.UNPROCESSABLE_ENTITY);
////        }
////    }
//    @Override
//    public String signup(User user) {
//        if (!userRepository.existsByUsername(user.getUsername())) {
//            user.setPassword(passwordEncoder.encode(user.getPassword()));
//            userRepository.save(user);
//            return jwtTokenProvider.createToken(user.getUsername(), user.getRoleNames());
//        } else {
//            throw new CustomException("Username is already in use", HttpStatus.UNPROCESSABLE_ENTITY);
//        }
//    }
//
//    @Override
//    public void delete(String username) {
//        userRepository.deleteByUsername(username);
//    }
//
//    @Override
//    public User search(String username) {
//        User user = userRepository.findByUsername(username);
//        if (user == null) {
//            throw new CustomException("The user doesn't exist", HttpStatus.NOT_FOUND);
//        }
//        return user;
//    }
//
//    @Override
//    public User whoami(HttpServletRequest req) {
//        return userRepository.findByUsername(jwtTokenProvider.getUsername(jwtTokenProvider.resolveToken(req)));
//    }
//
//    @Override
//    public String refresh(String username) {
//        return jwtTokenProvider.createToken(username, userRepository.findByUsername(username).getRoleNames());
//    }
//
//
//    @Override
//    public String createUser(UserDTO userDTO) throws EmailAlreadyExistsException {
//        if (userRepository.findByEmail(userDTO.getEmail()).isPresent()) {
//            throw new EmailAlreadyExistsException("Email already exists.");
//        }
//        {
//            User user = convertToEntity(userDTO);
//            user.setCreationDate(new Date());
//            user.setUpdateDate(new Date());
//            User savedUser = userRepository.save(user);
//            convertToDto(savedUser);
//            return jwtTokenProvider.createToken(user.getUsername(), user.getRoleNames());
//        }
//    }
//
//    @Override
//    public UserDTO convertToDto(User user) {
//        UserDTO userDTO = new UserDTO();
//        //userDTO.setId(user.getId());
//        //userDTO.setFirstName(user.getFirstName());
//        //userDTO.setLastName(user.getLastName());
//        userDTO.setEmail(user.getEmail());
//        // userDTO.setPhoneNumber(user.getPhoneNumber());
//        //userDTO.setGender(user.getGender());
//        //userDTO.setRole(user.getRole());
//        // userDTO.setCreationDate(user.getCreationDate());
//        // userDTO.setUpdateDate(user.getUpdateDate());
//        //userDTO.setProfileImage(user.getProfileImage());
//        return userDTO;
//    }
//
//
//    @Override
//    public User convertToEntity(UserDTO userDTO) {
//        User user = new User();
//        user.setFirstName(userDTO.getUsername());
//        user.setEmail(userDTO.getEmail());
//        user.setPassword(userDTO.getPassword());
//        return user;
//    }
//
//
////    @Autowired
////    private IUserRepo userRepository;
////
////    @Override
////    public UserDTO getUserById(int userId) {
////        User user = userRepository.findById(userId)
////                .orElseThrow(() -> new UserNotFoundException("User not found with id " + userId));
////        return convertToDto(user);
////    }
////
//
////
////    @Override
////    public UserDTO updateUser(int userId, UserDTO userDTO) {
////        User userToUpdate = userRepository.findById(userId)
////                .orElseThrow(() -> new UserNotFoundException("User not found with id " + userId));
////
////        userToUpdate.setFirstName(userDTO.getFirstName());
////        userToUpdate.setLastName(userDTO.getLastName());
////        userToUpdate.setEmail(userDTO.getEmail());
////        userToUpdate.setPhoneNumber(userDTO.getPhoneNumber());
////        userToUpdate.setGender(userDTO.getGender());
////        //userToUpdate.setRole(userDTO.getRole());
////        userToUpdate.setUpdateDate(new Date());
////        User updatedUser = userRepository.save(userToUpdate);
////        return convertToDto(updatedUser);
////    }
////
////    @Override
////    public void deleteUser(int userId) {
////        userRepository.deleteById(userId);
////    }
////
////
//
//    //
////
////    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
////
//    @Override
//    public User findUserByUserName(String userName) {
//        return userRepository.findByUsername(userName);
//    }
//
//    @Override
//    public User saveUser(User user) {
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
//        return userRepository.save(user);
//    }
//}
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
////@Service
////public class UserImpl implements IUserService {
////
////    @Autowired
////    IUserRepo iUserRepo ;
////
////    @Override
////    public List<User> getAlUsers() {
////        return iUserRepo.findAll() ;
////    }
////
////    @Override
////    public User getUser(int id) {
////        return iUserRepo.findById(id).get();
////    }
////
////    @Override
////    public User createUser(User user) {
////        return iUserRepo.save(user);
////    }
//////    @Override
//////    public User createUser(User user, MultipartFile profileImage) throws IOException {
//////        if (profileImage != null && !profileImage.isEmpty()) {
//////            Image image = new Image();
//////            image.setImageData(profileImage.getBytes());
//////            user.setProfileImage(image);
//////        }
//////        return iUserRepo.save(user);
//////    }
////
////
////    @Override
////    public User updateUser(User user){
////        return iUserRepo.save(user);
////    }
////
////    @Override
////    public void deleteUser(int id) {
////        iUserRepo.deleteById(id);
////    }
////}