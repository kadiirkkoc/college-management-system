package system.colluagemanagement.dtos;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
