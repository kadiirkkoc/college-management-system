package system.collegemanagement.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import system.collegemanagement.beans.AuthenticationResponse;
import system.collegemanagement.dtos.AuthenticationRequest;
import system.collegemanagement.dtos.UserDto;
import system.collegemanagement.model.User;
import system.collegemanagement.repository.UserRepository;
import system.collegemanagement.security.JwtService;

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
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.email(), request.password())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);

            Optional<User> userOptional = userRepository.findByEmail(request.email());
            if (!userOptional.isPresent()) {
                log.error("No user found with email: " + request.email());
                throw new UsernameNotFoundException("No user found with email: " + request.email());
            }

            String token = jwtService.generateToken(userOptional);
            return AuthenticationResponse.builder()
                    .token(token)
                    .build();

        } catch (AuthenticationException ex) {
            log.error("Authentication failed: " + ex.getMessage());
            throw new BadCredentialsException("Incorrect username or password");
        }
    }


}
