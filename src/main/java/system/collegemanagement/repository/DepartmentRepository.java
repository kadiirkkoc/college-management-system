package system.collegemanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import system.collegemanagement.model.Department;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {


}
