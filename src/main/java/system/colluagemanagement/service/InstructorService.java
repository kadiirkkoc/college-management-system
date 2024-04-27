package system.colluagemanagement.service;

import system.colluagemanagement.dtos.InstructorDto;
import system.colluagemanagement.dtos.LessonDto;

import java.util.List;

public interface InstructorService {

    List<InstructorDto> getAll();

    InstructorDto getById(Long id);

    String addInstructor(InstructorDto instructorDto);

    String updateInstructor(Long id, InstructorDto instructorDto);

    String deleteInstructor(Long id);
}
