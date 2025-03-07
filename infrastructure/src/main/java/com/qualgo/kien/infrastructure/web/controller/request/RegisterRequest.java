package com.qualgo.kien.infrastructure.web.controller.request;

import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
  String email;

  @Size(min = 4, max = 255)
  String username;

  @Size(min = 8, max = 30)
  String password;
}
