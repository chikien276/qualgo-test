package com.qualgo.kien.application.queryhandler;

import com.qualgo.kien.application.query.ChannelsQuery;
import com.qualgo.kien.domain.entity.ChatChannel;
import com.qualgo.kien.domain.entity.repository.ChatChannelRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ChannelsQueryHandler {
  final ChatChannelRepository chatChannelRepo;

  @QueryHandler
  public List<ChatChannel> handle(ChannelsQuery query) {
    return chatChannelRepo.findAll();
  }
}
