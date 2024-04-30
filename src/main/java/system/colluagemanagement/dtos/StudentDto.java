package system.colluagemanagement.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private Long departmentId;
    private List<Long> lessonIds;
}
