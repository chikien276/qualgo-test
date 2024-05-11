package com.qualgo.kien.application.dto;

import com.qualgo.kien.domain.entity.ChannelMessage;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageChannelMessage {
  List<ChannelMessage> messages;
  int pageSize;

  public boolean hasNextPage() {
    return !messages.isEmpty() && messages.size() == pageSize;
  }

  public String nextPageToken() {
    if (hasNextPage()) {
      return "" + messages.getLast().getId();
    }
    return null;
  }
}
