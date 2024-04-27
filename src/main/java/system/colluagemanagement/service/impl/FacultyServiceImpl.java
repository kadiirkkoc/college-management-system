package system.colluagemanagement.service.impl;

import system.colluagemanagement.dtos.FacultyDto;
import system.colluagemanagement.model.Faculty;
import org.springframework.stereotype.Service;
import system.colluagemanagement.repository.DepartmentRepository;
import system.colluagemanagement.repository.FacultyRepository;
import system.colluagemanagement.service.FacultyService;

import java.util.List;
import java.util.UUID;

@Service
public class  FacultyServiceImpl implements FacultyService {


    private final FacultyRepository facultyRepository;

    public FacultyServiceImpl(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }


    @Override
    public List<FacultyDto> getAll() {
        return List.of();
    }

    @Override
    public FacultyDto getById(Long id) {
        return null;
    }

    @Override
    public String addFaculty(FacultyDto facultyDto) {
        return "";
    }

    @Override
    public String updateFaculty(Long id, FacultyDto facultyDto) {
        return "";
    }

    @Override
    public String deleteFaculty(Long id) {
        return "";
    }
}