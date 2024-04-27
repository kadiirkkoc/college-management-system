package system.colluagemanagement.service.impl;

import org.springframework.http.HttpStatus;
import system.colluagemanagement.dtos.DepartmentDto;
import org.springframework.stereotype.Service;
import system.colluagemanagement.loggers.MainLogger;
import system.colluagemanagement.loggers.messages.DepartmentMessage;
import system.colluagemanagement.loggers.messages.FacultyMessage;
import system.colluagemanagement.model.Faculty;
import system.colluagemanagement.repository.DepartmentRepository;
import system.colluagemanagement.model.Department;
import system.colluagemanagement.repository.FacultyRepository;
import system.colluagemanagement.service.DepartmentService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class DepartmentServiceImpl implements DepartmentService {


    private final DepartmentRepository departmentRepository;
    private final MainLogger logger = new MainLogger(DepartmentServiceImpl.class);
    private final FacultyRepository facultyRepository;

    public DepartmentServiceImpl(DepartmentRepository departmentRepository, FacultyRepository facultyRepository) {
        this.departmentRepository = departmentRepository;
        this.facultyRepository = facultyRepository;
    }


    @Override
    public List<DepartmentDto> getAll() {
        return departmentRepository.findAll().stream()
                .map(dep -> DepartmentDto.builder()
                        .name(dep.getName())
                        .facultyId(String.valueOf(dep.getFaculty().getId()))
                        .build()).collect(Collectors.toList());
    }

    @Override
    public DepartmentDto getById(Long id) {
        Optional<Department> department = departmentRepository.findById(id);
        return department.map(dep -> DepartmentDto.builder()
                        .name(dep.getName())
                        .facultyId(String.valueOf(dep.getFaculty().getId()))
                        .build())
                .orElseGet(() -> {
                    logger.log(DepartmentMessage.NOT_FOUND, HttpStatus.BAD_REQUEST);
                    return null;
                }
        );
    }

    @Override
    public String addDepartment(DepartmentDto departmentDto) {
        Optional<Faculty> faculty = facultyRepository.findById(Long.parseLong(departmentDto.getFacultyId()));
        if (faculty.isEmpty()){
            logger.log(DepartmentMessage.NOT_FOUND, HttpStatus.BAD_REQUEST);
        }
            Department department = Department.builder()
                    .name(departmentDto.getName())
                    .faculty(faculty.get())
                    .uuid(UUID.randomUUID().toString())
                    .build();

            department = departmentRepository.save(department);

            return DepartmentMessage.CREATE + department.getId();
    }

    @Override
    public String updateDepartment(Long id, DepartmentDto departmentDto) {
        Optional<Department> department = departmentRepository.findById(id);
        if (department.isEmpty()){
            logger.log(DepartmentMessage.NOT_FOUND, HttpStatus.BAD_REQUEST);
        }
        department.get().setName(departmentDto.getName());
        departmentRepository.save(department.get());
        return DepartmentMessage.UPDATE + department.get().getId();
    }

    @Override
    public String deleteDepartment(Long id) {
        return "";
    }
}

