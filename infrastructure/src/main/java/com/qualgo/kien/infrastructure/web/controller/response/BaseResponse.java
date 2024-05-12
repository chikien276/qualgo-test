package com.qualgo.kien.infrastructure.web.controller.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BaseResponse<T> {
  int status = 200;
  T data;

  public static <T> BaseResponse<T> ok() {
    return new BaseResponse<>();
  }
}
