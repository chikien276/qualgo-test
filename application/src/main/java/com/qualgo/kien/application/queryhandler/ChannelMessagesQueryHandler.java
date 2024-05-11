package com.qualgo.kien.application.queryhandler;

import com.qualgo.kien.application.dto.PageChannelMessage;
import com.qualgo.kien.application.query.ChannelMessagesQuery;
import com.qualgo.kien.domain.entity.ChannelMessage;
import com.qualgo.kien.domain.entity.repository.ChannelMessageRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ChannelMessagesQueryHandler {
  public static final int PAGE_SIZE = 10;
  final ChannelMessageRepository channelMessageRepository;

  @QueryHandler
  public PageChannelMessage handle(ChannelMessagesQuery query) {
    List<ChannelMessage> channelMessages =
        channelMessageRepository.selectPage(
            query.getChannelId(), query.getLastChannelMessageId(), PAGE_SIZE);
    return PageChannelMessage.builder().messages(channelMessages).pageSize(PAGE_SIZE).build();
  }
}
