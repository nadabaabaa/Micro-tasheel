package tn.esprit.infini.Pidev.Repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.infini.Pidev.entities.ERole;
import tn.esprit.infini.Pidev.entities.Role;


import java.util.Optional;


@Repository
public interface IRoleRepo extends JpaRepository<Role,Integer> {
    Optional<Role> findByName(ERole name);
    boolean existsByName(ERole name);

}
