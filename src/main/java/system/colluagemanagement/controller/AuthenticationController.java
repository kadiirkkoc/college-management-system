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

    @GetMapping
    public String welcome(){
        return  "hello welcome";
    }

    @PostMapping("/registration")
    public ResponseEntity<AuthenticationResponse> registration(@RequestBody UserDto userDto){
        return new ResponseEntity<>(userService.createUser(userDto), HttpStatus.CREATED);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest authRequest){
        return new ResponseEntity<>(userService.authenticate(authRequest), HttpStatus.CREATED);
    }

    @GetMapping("/user")
    public String getUserString(){
        return "this is USER";
    }

    @GetMapping("/admin")
    public String getAdminString(){
        return "this is ADMIN";
    }
}
