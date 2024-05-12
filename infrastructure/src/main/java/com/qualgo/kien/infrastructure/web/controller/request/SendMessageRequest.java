package com.qualgo.kien.infrastructure.web.controller.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SendMessageRequest {
  String contentType;
  String content;
}
