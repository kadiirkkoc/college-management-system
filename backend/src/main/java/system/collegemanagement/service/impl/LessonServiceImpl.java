package system.collegemanagement.service.impl;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import system.collegemanagement.dtos.LessonDto;
import system.collegemanagement.loggers.MainLogger;
import system.collegemanagement.loggers.messages.DepartmentMessage;
import system.collegemanagement.loggers.messages.LessonMessage;
import system.collegemanagement.model.Department;
import system.collegemanagement.model.Instructor;
import system.collegemanagement.model.Lesson;
import system.collegemanagement.repository.DepartmentRepository;
import system.collegemanagement.repository.InstructorRepository;
import system.collegemanagement.repository.LessonRepository;
import system.collegemanagement.service.LessonService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class LessonServiceImpl implements LessonService {

    private final LessonRepository lessonRepository;
    private final DepartmentRepository departmentRepository;
    private final MainLogger logger = new MainLogger(LessonServiceImpl.class);
    private final InstructorRepository instructorRepository;

    public LessonServiceImpl(LessonRepository lessonRepository, DepartmentRepository departmentRepository, InstructorRepository instructorRepository) {
        this.lessonRepository = lessonRepository;
        this.departmentRepository = departmentRepository;
        this.instructorRepository = instructorRepository;
    }


    @Override
    public List<LessonDto> getAll() {
        return lessonRepository.findAll().stream()
                .map(lesson -> LessonDto.builder()
                        .courseBooks(lesson.getCourseBooks())
                        .courseAverage(lesson.getCourseAverage())
                        .courseCredits(lesson.getCourseCredits())
                        .courseCode(lesson.getCourseCode())
                        .courseDescription(lesson.getCourseDescription())
                        .courseHours(lesson.getCourseHours())
                        .courseLevel(lesson.getCourseLevel())
                        .courseSubject(lesson.getCourseSubject())
                        .courseLanguage(lesson.getCourseLanguage())
                        .departmentId(String.valueOf(lesson.getDepartment().getId()))
                        .instructorId(String.valueOf(lesson.getInstructor().getId()))
                        .build()).collect(Collectors.toList());
    }

    @Override
    public LessonDto getById(Long id) {
        Optional<Lesson> lesson = lessonRepository.findById(id);
        return lesson.map(x -> LessonDto.builder()
                        .courseBooks(x.getCourseBooks())
                        .courseAverage(x.getCourseAverage())
                        .courseCredits(x.getCourseCredits())
                        .courseCode(x.getCourseCode())
                        .courseDescription(x.getCourseDescription())
                        .courseHours(x.getCourseHours())
                        .courseLevel(x.getCourseLevel())
                        .courseSubject(x.getCourseSubject())
                        .courseLanguage(x.getCourseLanguage())
                        .departmentId(String.valueOf(x.getDepartment().getId()))
                        .instructorId(String.valueOf(x.getInstructor().getId()))
                        .build())
                .orElseGet(() -> {
                    logger.log(LessonMessage.NOT_FOUND, HttpStatus.BAD_REQUEST);
                    return null;
                });
    }

    @Override
    public String addLesson(LessonDto lessonDto) {
        Optional<Department> department = departmentRepository.findById(Long.valueOf(lessonDto.getDepartmentId()));
        Optional<Instructor> instructor = instructorRepository.findById(Long.valueOf(lessonDto.getInstructorId()));

        if (!department.isPresent() || !instructor.isPresent()){
            logger.log(DepartmentMessage.NOT_FOUND,HttpStatus.BAD_REQUEST);
        }
        Lesson lesson = Lesson.builder()
                .courseBooks(lessonDto.getCourseBooks())
                .courseAverage(lessonDto.getCourseAverage())
                .courseCredits(lessonDto.getCourseCredits())
                .courseCode(lessonDto.getCourseCode())
                .courseDescription(lessonDto.getCourseDescription())
                .courseHours(lessonDto.getCourseHours())
                .courseLevel(lessonDto.getCourseLevel())
                .courseSubject(lessonDto.getCourseSubject())
                .courseLanguage(lessonDto.getCourseLanguage())
                .uuid(UUID.randomUUID().toString())
                .department(department.get())
                .instructor(instructor.get())
                .build();
        lesson = lessonRepository.save(lesson);

        return LessonMessage.CREATE + lesson.getId();
    }

    @Override
    public String updateLesson(Long id, LessonDto lessonDto) {
        Optional<Lesson> lesson = lessonRepository.findById(id);
        if (!lesson.isPresent()){
            logger.log(LessonMessage.NOT_FOUND,HttpStatus.BAD_REQUEST);
        }
        lesson.get().setCourseAverage(lessonDto.getCourseAverage());
        lesson.get().setCourseBooks(lessonDto.getCourseBooks());
        lesson.get().setCourseCredits(lessonDto.getCourseCredits());
        lesson.get().setCourseDescription(lessonDto.getCourseDescription());
        lesson.get().setCourseHours(lessonDto.getCourseHours());
        lesson.get().setCourseLanguage(lessonDto.getCourseLanguage());
        lesson.get().setCourseLevel(lessonDto.getCourseLevel());

        lessonRepository.save(lesson.get());
        return LessonMessage.UPDATE + lesson.get().getId();
    }

    @Override
    public String deleteLesson(Long id) {
        Optional<Lesson> lesson = lessonRepository.findById(id);

        if (!lesson.isPresent()) {
            return LessonMessage.NOT_FOUND + id;
        }

        if (!lesson.get().getStudents().isEmpty()) {
            return "Cannot delete lesson with ID " + id;
        }

        lessonRepository.deleteById(id);
        return LessonMessage.DELETE + id;
    }

}
