package system.collegemanagement.service;

import system.collegemanagement.dtos.LessonDto;

import java.util.List;

public interface LessonService {

    List<LessonDto> getAll();

    LessonDto getById(Long id);

    String addLesson(LessonDto lessonDto);

    String updateLesson(Long id, LessonDto lessonDto);

    String deleteLesson(Long id);
}
