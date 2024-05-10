package com.qualgo.kien.application.commandhandler;

import com.qualgo.kien.application.command.CreateChannelCommand;
import com.qualgo.kien.domain.entity.ChatChannel;
import com.qualgo.kien.domain.entity.repository.ChatChannelRepository;
import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateChannelHandler {
  final ChatChannelRepository channelRepo;

  @CommandHandler
  public Long createChannel(CreateChannelCommand command) {
    ChatChannel chatChannel =
        ChatChannel.builder()
            .name(command.getChannelName())
            .creatorUserInfoId(command.getUserId())
            .build();
    channelRepo.insert(chatChannel);
    return chatChannel.getId();
  }
}
