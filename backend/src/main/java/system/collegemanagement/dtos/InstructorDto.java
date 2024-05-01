package system.collegemanagement.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class InstructorDto {

    private String name;
    private String email;
    private String phone;
    private String password;
    private String departmentId;
}
