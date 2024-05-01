package system.collegemanagement.dtos;


public record AuthenticationRequest(
        String email,
        String password
) {
}
