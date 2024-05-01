package system.collegemanagement.service.impl;

import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import system.collegemanagement.dtos.InstructorDto;
import system.collegemanagement.loggers.MainLogger;
import system.collegemanagement.loggers.messages.DepartmentMessage;
import system.collegemanagement.loggers.messages.InstructorMessage;
import system.collegemanagement.model.*;
import system.collegemanagement.repository.DepartmentRepository;
import system.collegemanagement.repository.InstructorRepository;
import system.collegemanagement.repository.LessonRepository;
import system.collegemanagement.repository.UserRepository;
import system.collegemanagement.service.InstructorService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class InstructorServiceImpl implements InstructorService {

    private static final Logger log = LoggerFactory.getLogger(InstructorServiceImpl.class);
    private final InstructorRepository instructorRepository;
    private final MainLogger logger = new MainLogger(InstructorServiceImpl.class);
    private final DepartmentRepository departmentRepository;
    private final LessonRepository lessonRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public InstructorServiceImpl(InstructorRepository instructorRepository, DepartmentRepository departmentRepository, LessonRepository lessonRepository, BCryptPasswordEncoder passwordEncoder ,UserRepository userRepository) {
        this.instructorRepository = instructorRepository;
        this.departmentRepository = departmentRepository;
        this.lessonRepository = lessonRepository;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }


    @Override
    public List<InstructorDto> getAll() {
        return instructorRepository.findAll().stream()
                .map(instructor -> InstructorDto.builder()
                        .name(instructor.getName())
                        .email(instructor.getEmail())
                        .phone(instructor.getPhone())
                        .build()).collect(Collectors.toList());
    }

    @Override
    public InstructorDto getById(Long id) {
        Optional<Instructor> instructor = instructorRepository.findById(id);
        return instructor.map(x -> InstructorDto.builder()
                        .name(x.getName())
                        .email(x.getEmail())
                        .phone(x.getPhone())
                        .build())
                .orElseGet(() -> {
                    logger.log(InstructorMessage.NOT_FOUND,HttpStatus.BAD_REQUEST);
                    return null;
                });
    }


    @Override
    public String addInstructor(InstructorDto instructorDto) {
        Optional<Department> department = departmentRepository.findById(Long.valueOf(instructorDto.getDepartmentId()));
        if (!department.isPresent()){
            return DepartmentMessage.NOT_FOUND;
        }

        User newUser = User.builder()
                .email(instructorDto.getEmail())
                .password(passwordEncoder.encode(instructorDto.getPassword()))
                .userRole(UserRole.INSTRUCTOR)
                .build();
        userRepository.save(newUser);

        Instructor instructor = Instructor.builder()
                .user(newUser)
                .name(instructorDto.getName())
                .email(instructorDto.getEmail())
                .phone(instructorDto.getPhone())
                .uuid(UUID.randomUUID().toString())
                .build();
        instructor = instructorRepository.save(instructor);
        return InstructorMessage.CREATE + instructor.getId();
    }



    @Override
    public String updateInstructor(Long id, InstructorDto instructorDto) {
        Optional<Instructor> instructor = instructorRepository.findById(id);
        if (!instructor.isPresent()){
            logger.log(InstructorMessage.NOT_FOUND,HttpStatus.BAD_REQUEST);
        }
        instructor.get().setName(instructorDto.getName());
        instructor.get().setEmail(instructorDto.getEmail());
        instructor.get().setPhone(instructorDto.getPhone());
        instructor.get().setPassword(passwordEncoder.encode(instructorDto.getPassword()));
        instructorRepository.save(instructor.get());

        return InstructorMessage.UPDATE + instructor.get().getId();
    }

    @Transactional
    @Override
    public String deleteInstructor(Long id) {
        Optional<Instructor> instructor = instructorRepository.findById(id);
        if (!instructor.isPresent()) {
            return InstructorMessage.NOT_FOUND + id;
        }

        List<Lesson> lessons = instructor.get().getLessons();
        if (lessons != null && !lessons.isEmpty()) {
            for (Lesson lesson : lessons) {
                lesson.setInstructor(null);
            }
            lessonRepository.saveAll(lessons);
        }
        instructorRepository.delete(instructor.get());
        return InstructorMessage.DELETE + id;
    }
}
