package system.colluagemanagement.service;

import system.colluagemanagement.dtos.DepartmentDto;
import system.colluagemanagement.dtos.StudentDto;

import java.util.List;


public interface StudentService {

    List<StudentDto> getAll();

    StudentDto getById(Long id);

    String addStudent(StudentDto studentDto);

    String updateStudent(Long id, StudentDto studentDto);

    String deleteStudent(Long id);
}
