package system.colluagemanagement.repository;

import system.colluagemanagement.model.Faculty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface FacultyRepository extends JpaRepository<Faculty, Long> {

}
