package system.colluagemanagement.service.impl;

import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import system.colluagemanagement.dtos.DepartmentDto;
import system.colluagemanagement.dtos.FacultyDto;
import system.colluagemanagement.dtos.InstructorDto;
import system.colluagemanagement.loggers.MainLogger;
import system.colluagemanagement.loggers.messages.DepartmentMessage;
import system.colluagemanagement.loggers.messages.InstructorMessage;
import system.colluagemanagement.model.Department;
import system.colluagemanagement.model.Faculty;
import system.colluagemanagement.model.Instructor;
import system.colluagemanagement.model.Lesson;
import system.colluagemanagement.repository.DepartmentRepository;
import system.colluagemanagement.repository.InstructorRepository;
import system.colluagemanagement.repository.LessonRepository;
import system.colluagemanagement.service.InstructorService;

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

    public InstructorServiceImpl(InstructorRepository instructorRepository, DepartmentRepository departmentRepository, LessonRepository lessonRepository) {
        this.instructorRepository = instructorRepository;
        this.departmentRepository = departmentRepository;
        this.lessonRepository = lessonRepository;
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
            logger.log(DepartmentMessage.NOT_FOUND,HttpStatus.BAD_REQUEST);
        }
        Instructor instructor = Instructor.builder()
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
