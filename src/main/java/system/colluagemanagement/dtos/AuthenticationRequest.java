package system.colluagemanagement.dtos;


public record AuthenticationRequest(
        String email,
        String password
) {
}
