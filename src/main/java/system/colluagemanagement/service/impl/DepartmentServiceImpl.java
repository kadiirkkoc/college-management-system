package system.colluagemanagement.service.impl;

import system.colluagemanagement.dtos.DepartmentDto;
import org.springframework.stereotype.Service;
import system.colluagemanagement.repository.DepartmentRepository;
import system.colluagemanagement.model.Department;
import system.colluagemanagement.service.DepartmentService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DepartmentServiceImpl implements DepartmentService {


    private final DepartmentRepository departmentRepository;

    public DepartmentServiceImpl(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }


    @Override
    public List<DepartmentDto> getAll() {
        return List.of();
    }

    @Override
    public DepartmentDto getById(Long id) {
        return null;
    }

    @Override
    public String addDepartment(DepartmentDto departmentDto) {
        return "";
    }

    @Override
    public String updateDepartment(Long id, DepartmentDto departmentDto) {
        return "";
    }

    @Override
    public String deleteDepartment(Long id) {
        return "";
    }
}
