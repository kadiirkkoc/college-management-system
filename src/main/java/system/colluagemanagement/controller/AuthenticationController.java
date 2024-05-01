package system.colluagemanagement.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import system.colluagemanagement.beans.AuthenticationResponse;
import system.colluagemanagement.dtos.AuthenticationRequest;
import system.colluagemanagement.dtos.UserDto;
import system.colluagemanagement.service.impl.UserServiceImpl;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final UserServiceImpl userService;

    public AuthenticationController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @PostMapping("/registration")
    public ResponseEntity<AuthenticationResponse> registration(@RequestBody UserDto userDto){
        return new ResponseEntity<>(userService.createUser(userDto), HttpStatus.CREATED);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest authRequest){
        return new ResponseEntity<>(userService.authenticate(authRequest), HttpStatus.CREATED);
    }

}
