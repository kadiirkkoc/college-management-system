package system.colluagemanagement.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import system.colluagemanagement.model.UserRole;

@Data
@Builder
@AllArgsConstructor
public class UserDto {

    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private String password;
    private UserRole userRole;
}
