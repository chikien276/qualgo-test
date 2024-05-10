package com.qualgo.kien.application.commandhandler;

import com.qualgo.kien.application.command.DeleteMessageCommand;
import com.qualgo.kien.application.exception.ChannelNotFoundException;
import com.qualgo.kien.application.exception.ForbiddenException;
import com.qualgo.kien.domain.entity.ChannelMessage;
import com.qualgo.kien.domain.entity.ChatChannel;
import com.qualgo.kien.domain.entity.repository.ChannelMessageRepository;
import com.qualgo.kien.domain.entity.repository.ChatChannelRepository;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeleteMessageHandler {

  final ChatChannelRepository chatChannelRepository;

  final ChannelMessageRepository channelMessageRepository;

  @CommandHandler
  public void handle(DeleteMessageCommand command) throws ChannelNotFoundException {
    ChatChannel channel = chatChannelRepository.findById(command.getChannelId());
    if (channel == null) {
      throw new ChannelNotFoundException(command.getChannelId());
    }

    ChannelMessage message = channelMessageRepository.findById(command.getMessageId());
    // Idempotent, no need to do anything else
    if (message == null) {
      return;
    }
    if (!Objects.equals(command.getUserId(), message.getSenderUserInfoId())
        || !Objects.equals(message.getChatChannelId(), command.getChannelId())) {
      throw new ForbiddenException();
    }
    channelMessageRepository.deleteById(command.getMessageId());
  }
}
