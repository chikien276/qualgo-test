package com.qualgo.kien.application.query;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChannelMessagesQuery {
  Long lastChannelMessageId;
  Long channelId;
}
