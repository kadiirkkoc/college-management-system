package system.colluagemanagement.service.impl;

import org.springframework.stereotype.Service;
import system.colluagemanagement.dtos.InstructorDto;
import system.colluagemanagement.repository.InstructorRepository;
import system.colluagemanagement.service.InstructorService;

import java.util.List;

@Service
public class InstructorServiceImpl implements InstructorService {

    private final InstructorRepository instructorRepository;

    public InstructorServiceImpl(InstructorRepository instructorRepository) {
        this.instructorRepository = instructorRepository;
    }

    @Override
    public List<InstructorDto> getAll() {
        return List.of();
    }

    @Override
    public InstructorDto getById(Long id) {
        return null;
    }

    @Override
    public String addInstructor(InstructorDto instructorDto) {
        return "";
    }

    @Override
    public String updateInstructor(Long id, InstructorDto instructorDto) {
        return "";
    }

    @Override
    public String deleteInstructor(Long id) {
        return "";
    }
}
