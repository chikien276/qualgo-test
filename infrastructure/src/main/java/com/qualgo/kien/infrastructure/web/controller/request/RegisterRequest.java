package com.qualgo.kien.infrastructure.web.controller.request;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
  String email;

  @Size(min = 4, max = 255)
  String username;

  @Size(min = 8, max = 30)
  String password;
}
