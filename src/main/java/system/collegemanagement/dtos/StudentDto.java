package system.collegemanagement.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class StudentDto {

    private String firstName;
    private String lastName;
    private String email;
    private String semester;
    private String phoneNumber;
    private String password;
    private Long departmentId;
    private List<Long> lessonIds;
}
