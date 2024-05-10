package com.qualgo.kien.application.commandhandler;

import com.qualgo.kien.application.command.SendMessageCommand;
import com.qualgo.kien.application.exception.ChannelNotFoundException;
import com.qualgo.kien.domain.entity.ChannelMessage;
import com.qualgo.kien.domain.entity.ChatChannel;
import com.qualgo.kien.domain.entity.repository.ChannelMessageRepository;
import com.qualgo.kien.domain.entity.repository.ChatChannelRepository;
import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SendChatMessageHandler {
  final ChatChannelRepository chatChannelRepository;

  final ChannelMessageRepository channelMessageRepository;

  @CommandHandler
  public Long handle(SendMessageCommand command) throws ChannelNotFoundException {
    ChatChannel channel = chatChannelRepository.findById(command.getChannelId());
    if (channel == null) {
      throw new ChannelNotFoundException(command.getChannelId());
    }
    ChannelMessage msg =
        ChannelMessage.builder()
            .contentBody(command.getContent())
            .contentType(command.getContentType())
            .chatChannelId(channel.getId())
            .senderUserInfoId(command.getUserId())
            .build();

    channelMessageRepository.insert(msg);
    return msg.getId();
  }
}
