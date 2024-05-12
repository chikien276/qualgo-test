package com.qualgo.kien.application.command;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateChannelCommand {
  String channelName;
  Long userId;
}
