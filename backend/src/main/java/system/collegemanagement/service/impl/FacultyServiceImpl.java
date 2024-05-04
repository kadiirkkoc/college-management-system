package system.collegemanagement.service.impl;

import org.springframework.http.HttpStatus;
import system.collegemanagement.dtos.FacultyDto;
import system.collegemanagement.loggers.MainLogger;
import system.collegemanagement.loggers.messages.FacultyMessage;
import system.collegemanagement.model.Faculty;
import org.springframework.stereotype.Service;
import system.collegemanagement.repository.FacultyRepository;
import system.collegemanagement.service.FacultyService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class  FacultyServiceImpl implements FacultyService {


    private final FacultyRepository facultyRepository;
    private final MainLogger logger = new MainLogger(FacultyServiceImpl.class);

    public FacultyServiceImpl(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }


    @Override
    public List<FacultyDto> getAll() {
        return facultyRepository.findAll().stream()
                .map(faculty -> FacultyDto.builder()
                        .id(faculty.getId())
                        .name(faculty.getName())
                        .campus(faculty.getCampus())
                        .build()).collect(Collectors.toList());
    }

    @Override
    public FacultyDto getById(Long id) {
        Optional<Faculty> faculty = facultyRepository.findById(id);
        return faculty.map(x -> FacultyDto.builder()
                        .name(faculty.get().getName())
                        .campus(faculty.get().getCampus())
                        .build())
                .orElseGet(() -> {
                    logger.log(FacultyMessage.NOT_FOUND, HttpStatus.BAD_REQUEST);
                    return null;
                }
        );
    }

    @Override
    public String addFaculty(FacultyDto facultyDto) {
        Faculty faculty = new Faculty();
        faculty.setName(facultyDto.getName());
        faculty.setUuid(UUID.randomUUID().toString());
        faculty.setCampus(facultyDto.getCampus());
        facultyRepository.save(faculty);
        return FacultyMessage.CREATE + faculty.getId();
    }

    @Override
    public String updateFaculty(Long id, FacultyDto facultyDto) {
        Optional<Faculty> faculty = facultyRepository.findById(id);
        if (!faculty.isPresent()) {
            logger.log(FacultyMessage.NOT_FOUND + id,HttpStatus.BAD_REQUEST);
        }

        faculty.get().setName(facultyDto.getName());
        faculty.get().setCampus(facultyDto.getCampus());
        facultyRepository.save(faculty.get());
        return FacultyMessage.UPDATE + faculty.get().getId();
    }

    @Override
    public String deleteFaculty(Long id) {
        facultyRepository.deleteById(id);
        return FacultyMessage.DELETE + id;
    }
}