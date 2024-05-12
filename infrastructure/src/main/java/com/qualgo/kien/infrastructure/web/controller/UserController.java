package com.qualgo.kien.infrastructure.web.controller;

import com.qualgo.kien.application.command.RegisterCommand;
import com.qualgo.kien.infrastructure.web.config.JwtService;
import com.qualgo.kien.infrastructure.web.controller.request.LoginRequest;
import com.qualgo.kien.infrastructure.web.controller.request.RegisterRequest;
import com.qualgo.kien.infrastructure.web.controller.response.LoginResponse;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class UserController {

  final JwtService jwtService;

  final AuthenticationManager authenticationManager;

  final CommandGateway commandGateway;

  @PostMapping("/register")
  @PermitAll
  public String addNewUser(@RequestBody @Valid RegisterRequest request) {
    commandGateway.sendAndWait(
        RegisterCommand.builder()
            .email(request.getEmail())
            .password(request.getPassword())
            .username(request.getUsername())
            .build());
    return "Ok";
  }

  @PostMapping("/login")
  @PermitAll
  public LoginResponse login(@RequestBody@Valid LoginRequest loginRequest) {
    Authentication authentication =
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                loginRequest.getUsername(), loginRequest.getPassword()));
    if (authentication.isAuthenticated()) {
      String token = jwtService.generateToken(loginRequest.getUsername());
      return LoginResponse.builder().token(token).build();
    } else {
      throw new UsernameNotFoundException("Invalid username or password request !");
    }
  }
}
