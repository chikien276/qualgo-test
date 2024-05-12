package com.qualgo.kien.application.command;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterCommand {

  private String email;

  private String username;

  private String password;
}
