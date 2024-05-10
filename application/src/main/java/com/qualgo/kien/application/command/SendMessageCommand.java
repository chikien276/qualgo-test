package com.qualgo.kien.application.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SendMessageCommand {
  Long userId;

  Long channelId;

  String contentType;

  String content;
}
