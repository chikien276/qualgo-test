package com.qualgo.kien.application.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeleteMessageCommand {
  Long userId;

  Long channelId;

  Long messageId;
}
