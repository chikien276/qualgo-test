package com.qualgo.kien.application.commandhandler;

import com.qualgo.kien.application.command.RegisterCommand;
import com.qualgo.kien.application.exception.UserExistException;
import com.qualgo.kien.domain.entity.UserInfo;
import com.qualgo.kien.domain.entity.repository.UserInfoRepository;
import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserRegisterHandler {

  final UserInfoRepository userInfoRepository;

  final PasswordEncoder passwordEncoder;

  @CommandHandler
  public void handle(RegisterCommand command) throws UserExistException {
    UserInfo existUser = userInfoRepository.findByUserName(command.getUsername());
    if (existUser != null) {
      throw new UserExistException(command.getUsername());
    }
    UserInfo newUser =
        UserInfo.builder()
            .username(command.getUsername())
            .email(command.getEmail())
            .password(passwordEncoder.encode(command.getPassword()))
            .build();
    userInfoRepository.insert(newUser);
  }
}
