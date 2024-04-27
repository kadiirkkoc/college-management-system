package system.colluagemanagement.service.impl;

import org.springframework.stereotype.Service;
import system.colluagemanagement.dtos.StudentDto;
import system.colluagemanagement.repository.StudentRepository;
import system.colluagemanagement.service.StudentService;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public List<StudentDto> getAll() {
        return List.of();
    }

    @Override
    public StudentDto getById(Long id) {
        return null;
    }

    @Override
    public String addStudent(StudentDto studentDto) {
        return "";
    }

    @Override
    public String updateStudent(Long id, StudentDto studentDto) {
        return "";
    }

    @Override
    public String deleteStudent(Long id) {
        return "";
    }
}
