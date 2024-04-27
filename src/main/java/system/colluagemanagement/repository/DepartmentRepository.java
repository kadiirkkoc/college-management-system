package system.colluagemanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import system.colluagemanagement.model.Department;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {


}
