package system.collegemanagement.service.impl;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import system.collegemanagement.dtos.StudentDto;
import system.collegemanagement.loggers.MainLogger;
import system.collegemanagement.loggers.messages.DepartmentMessage;
import system.collegemanagement.loggers.messages.StudentMessage;
import system.collegemanagement.model.*;
import system.collegemanagement.repository.DepartmentRepository;
import system.collegemanagement.repository.LessonRepository;
import system.collegemanagement.repository.StudentRepository;
import system.collegemanagement.repository.UserRepository;
import system.collegemanagement.service.StudentService;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final DepartmentRepository departmentRepository;
    private final LessonRepository lessonRepository;
    private final MainLogger logger = new MainLogger(LessonServiceImpl.class);
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public StudentServiceImpl(StudentRepository studentRepository, DepartmentRepository departmentRepository, LessonRepository lessonRepository, UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.studentRepository = studentRepository;
        this.departmentRepository = departmentRepository;
        this.lessonRepository = lessonRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = bCryptPasswordEncoder;
    }


    @Override
    public List<StudentDto> getAll() {
        return studentRepository.findAll().stream()
                .map(student -> StudentDto.builder()
                        .firstName(student.getFirstName())
                        .lastName(student.getLastName())
                        .email(student.getEmail())
                        .semester(student.getSemester())
                        .phoneNumber(student.getPhoneNumber())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public StudentDto getById(Long id) {
        Optional<Student> student = studentRepository.findById(id);
        return student.map(x -> StudentDto.builder()
                        .firstName(x.getFirstName())
                        .lastName(x.getLastName())
                        .email(x.getEmail())
                        .semester(x.getSemester())
                        .phoneNumber(x.getPhoneNumber())
                        .build())
                .orElseGet(() -> {
                    logger.log(StudentMessage.NOT_FOUND, HttpStatus.BAD_REQUEST);
                    return null;
                });
    }

    @Override
    public String addStudent(StudentDto studentDto) {
        Optional<Department> department = departmentRepository.findById(Long.valueOf(studentDto.getDepartmentId()));
        if (!department.isPresent()) {
            return DepartmentMessage.NOT_FOUND + studentDto.getDepartmentId();
        }

        User newUser = User.builder()
                .email(studentDto.getEmail())
                .password(passwordEncoder.encode(studentDto.getPassword()))
                .userRole(UserRole.INSTRUCTOR)
                .build();
        userRepository.save(newUser);

        Student student = Student.builder()
                .firstName(studentDto.getFirstName())
                .lastName(studentDto.getLastName())
                .email(studentDto.getEmail())
                .semester(studentDto.getSemester())
                .phoneNumber(studentDto.getPhoneNumber())
                .uuid(UUID.randomUUID().toString())
                .userRole(UserRole.valueOf("STUDENT"))
                .build();


        if (studentDto.getLessonIds() != null && !studentDto.getLessonIds().isEmpty()) {
            Set<Lesson> lessons = new HashSet<>();
            for (Long lessonId : studentDto.getLessonIds()) {
                Optional<Lesson> lessonOptional = lessonRepository.findById(lessonId);
                if (!lessonOptional.isPresent()) {
                    logger.log(StudentMessage.NOT_FOUND, HttpStatus.BAD_REQUEST);
                }
                lessons.add(lessonOptional.get());
            }
            student.setLessons(lessons);
            studentRepository.save(student);
        }

        return StudentMessage.CREATE + student.getId();
    }


    @Override
    public String updateStudent(Long id, StudentDto studentDto) {
        Optional<Student> student = studentRepository.findById(id);
        if (!student.isPresent()) {
            return StudentMessage.NOT_FOUND + id;
        }

        student.get().setFirstName(studentDto.getFirstName());
        student.get().setLastName(studentDto.getLastName());
        student.get().setEmail(studentDto.getEmail());
        student.get().setSemester(studentDto.getSemester());
        student.get().setPhoneNumber(studentDto.getPhoneNumber());

        studentRepository.save(student.get());
        return StudentMessage.UPDATE + student.get().getId();
    }

    @Override
    public String deleteStudent(Long id) {
        Optional<Student> student = studentRepository.findById(id);
        if (!student.isPresent()) {
            return StudentMessage.NOT_FOUND + id;
        }

        if (!student.get().getLessons().isEmpty()) {
            for (Lesson lesson : student.get().getLessons()) {
                lesson.getStudents().remove(student);
            }
        }
        studentRepository.deleteById(id);
        return StudentMessage.DELETE + id;
    }
}
