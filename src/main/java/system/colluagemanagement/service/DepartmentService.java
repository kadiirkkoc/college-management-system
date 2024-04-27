package system.colluagemanagement.service;


import system.colluagemanagement.dtos.DepartmentDto;

import java.util.List;

public interface DepartmentService {

    List<DepartmentDto> getAll();

    DepartmentDto getById(Long id);

    String addDepartment(DepartmentDto departmentDto);

    String updateDepartment(Long id, DepartmentDto departmentDto);

    String deleteDepartment(Long id);
}
