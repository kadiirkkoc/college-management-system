package system.collegemanagement.service.impl;

import org.springframework.http.HttpStatus;
import system.collegemanagement.dtos.DepartmentDto;
import org.springframework.stereotype.Service;
import system.collegemanagement.loggers.MainLogger;
import system.collegemanagement.loggers.messages.DepartmentMessage;
import system.collegemanagement.loggers.messages.FacultyMessage;
import system.collegemanagement.model.Faculty;
import system.collegemanagement.repository.DepartmentRepository;
import system.collegemanagement.model.Department;
import system.collegemanagement.repository.FacultyRepository;
import system.collegemanagement.service.DepartmentService;

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
        Optional<Faculty> faculty = facultyRepository.findById(Long.valueOf(departmentDto.getFacultyId()));
        if (!faculty.isPresent()){
            logger.log(FacultyMessage.NOT_FOUND, HttpStatus.BAD_REQUEST);
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
        if (!department.isPresent()){
            logger.log(DepartmentMessage.NOT_FOUND, HttpStatus.BAD_REQUEST);
        }
        department.get().setName(departmentDto.getName());
        departmentRepository.save(department.get());
        return DepartmentMessage.UPDATE + department.get().getId();
    }

    @Override
    public String deleteDepartment(Long id) {
        Optional<Department> department = departmentRepository.findById(id);
        if (!department.isPresent()) {
            logger.log(DepartmentMessage.NOT_FOUND, HttpStatus.BAD_REQUEST);
        }
        departmentRepository.deleteById(id);
        return DepartmentMessage.DELETE + id;
    }
}

