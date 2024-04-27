package system.colluagemanagement.service.impl;

import org.springframework.stereotype.Service;
import system.colluagemanagement.dtos.LessonDto;
import system.colluagemanagement.repository.LessonRepository;
import system.colluagemanagement.service.LessonService;

import java.util.List;

@Service
public class LessonServiceImpl implements LessonService {

    private final LessonRepository lessonRepository;

    public LessonServiceImpl(LessonRepository lessonRepository) {
        this.lessonRepository = lessonRepository;
    }

    @Override
    public List<LessonDto> getAll() {
        return List.of();
    }

    @Override
    public LessonDto getById(Long id) {
        return null;
    }

    @Override
    public String addLesson(LessonDto lessonDto) {
        return "";
    }

    @Override
    public String updateLesson(Long id, LessonDto lessonDto) {
        return "";
    }

    @Override
    public String deleteLesson(Long id) {
        return "";
    }
}
