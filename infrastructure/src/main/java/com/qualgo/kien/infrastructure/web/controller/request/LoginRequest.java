package com.qualgo.kien.infrastructure.web.controller.request;

import com.baomidou.mybatisplus.annotation.TableId;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {
  @Size(min = 4, max = 255)
  @TableId()
  String username;

  @Size(min = 8, max = 255)
  String password;
}
