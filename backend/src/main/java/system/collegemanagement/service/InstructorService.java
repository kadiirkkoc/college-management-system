package system.collegemanagement.service;

import system.collegemanagement.dtos.InstructorDto;

import java.util.List;

public interface InstructorService {

    List<InstructorDto> getAll();

    InstructorDto getById(Long id);

    String addInstructor(InstructorDto instructorDto);

    String updateInstructor(Long id, InstructorDto instructorDto);

    String deleteInstructor(Long id);
}
