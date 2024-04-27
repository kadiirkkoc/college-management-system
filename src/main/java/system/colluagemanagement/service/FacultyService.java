package system.colluagemanagement.service;

import system.colluagemanagement.dtos.FacultyDto;
import system.colluagemanagement.dtos.InstructorDto;

import java.util.List;

public interface FacultyService {

    List<FacultyDto> getAll();

    FacultyDto getById(Long id);

    String addFaculty(FacultyDto facultyDto);

    String updateFaculty(Long id, FacultyDto facultyDto);

    String deleteFaculty(Long id);
}
