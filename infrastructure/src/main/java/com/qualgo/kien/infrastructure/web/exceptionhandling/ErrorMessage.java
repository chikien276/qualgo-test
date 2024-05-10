package com.qualgo.kien.infrastructure.web.exceptionhandling;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ErrorMessage {
  String message;

  int errorCode;
}
