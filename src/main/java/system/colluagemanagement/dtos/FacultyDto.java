package system.colluagemanagement.dtos;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class FacultyDto {

    private String name;
    private String campus;
}
