package system.collegemanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import system.collegemanagement.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByEmail(String username);
}
