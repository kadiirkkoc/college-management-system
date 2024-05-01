package system.colluagemanagement.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import system.colluagemanagement.beans.AuthenticationResponse;
import system.colluagemanagement.dtos.AuthenticationRequest;
import system.colluagemanagement.dtos.UserDto;
import system.colluagemanagement.model.UserRole;
import system.colluagemanagement.model.User;
import system.colluagemanagement.repository.UserRepository;
import system.colluagemanagement.security.JwtService;

import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class UserServiceImpl{

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }


    public AuthenticationResponse createUser(UserDto userDto) {
        var dbUser = User.builder()
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .username(userDto.getUsername())
                .email(userDto.getEmail())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .uuid(UUID.randomUUID().toString())
                .userRole(userDto.getUserRole())
                .build();
        userRepository.save(dbUser);
        String token = jwtService.generateToken(Optional.of(dbUser));
        return AuthenticationResponse.builder()
                .token(token)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(),request.password())
        );
        var user = userRepository.findByEmail(request.email());

        String token = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(token)
                .build();
    }
}
