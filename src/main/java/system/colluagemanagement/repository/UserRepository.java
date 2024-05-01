package system.colluagemanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import system.colluagemanagement.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByEmail(String username);
}
