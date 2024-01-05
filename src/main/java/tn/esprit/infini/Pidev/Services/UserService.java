package tn.esprit.infini.Pidev.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.infini.Pidev.Repository.Creditrepository;
import tn.esprit.infini.Pidev.Repository.UserRepository;

import tn.esprit.infini.Pidev.entities.Pack;
import tn.esprit.infini.Pidev.entities.User;

import java.util.ArrayList;

import tn.esprit.infini.Pidev.entities.User;

import java.sql.Date;
import java.time.LocalDate;
import java.time.Period;

import java.util.List;
import jakarta.persistence.*;


@Service
//@AllArgsConstructor
public class UserService implements IUser{
    @Autowired
    UserRepository UR;
    Creditrepository cr;
    @Autowired
    IUser iUser;
    //CRUD
    @Override
    public User addUser(User user) {
        //UR.findById(user.getId()).get().setType(TypeUser.Potential_Client);
        return UR.save(user);}
    @Override
    public List<User> retrieveAllUsers() {return (List<User>) UR.findAll();}
    @Override
    public User updateUser(User user) {return UR.save(user);}
    @Override
    public void deleteUser(Integer idUser) {UR.deleteById(idUser);}
    //Advanced functions
    @Override
    public User retrieveUser(int idUser) {return UR.findById(idUser);}

   // @Override
    //public User retrieveUserByLogin(String login) {return UR.findByLogin(login);}

    @Override
    public Boolean login(String login, String mdp) {
        /*if (UR.findByLogin(login)!=null){if (UR.findByLogin(login).getPassword()==mdp) {
            UR.findByLogin(login).setNombreTentatives(0);
            return true;
        }
            else if (UR.findByLogin(login).getNombreTentatives()==5) return false;
            else {
            UR.findByLogin(login).setNombreTentatives(UR.findByLogin(login).getNombreTentatives()+1);
                return false;
        }}
        else */return false;
    }

    @Override
    public void changePassword(User user, String mdp) {user.setPassword(mdp);UR.save(user);}

    @Override
    public boolean veriyUserPassword(User user, String password) {
        if (user.getPassword().matches(password)==true)return true;
        else return false;
    }

    @Override
    public int countUsers() {
        Long nbr= UR.count();
        return nbr.intValue();

    }

    @Override
    public int countAge(int idUser) {
        User u=iUser.retrieveUser(idUser);
        LocalDate birthdateLocal = new Date(u.getBirthdate().getTime()).toLocalDate();
        return Period.between(birthdateLocal, LocalDate.now()).getYears();
    }

    @Override
    public List<User> findAllByTypeEndingWith(String s) {
        return UR.findAll();
    }

    @Override
    public void banUser(User user) {

    }
    @Override
    public User findUserByCreditId(Long creditId) {
        return UR.findUserByCreditId(creditId);
    }



}
