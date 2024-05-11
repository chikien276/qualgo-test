package com.qualgo.kien.infrastructure.web.controller;

import com.qualgo.kien.application.command.CreateChannelCommand;
import com.qualgo.kien.application.command.DeleteMessageCommand;
import com.qualgo.kien.application.command.SendMessageCommand;
import com.qualgo.kien.application.dto.PageChannelMessage;
import com.qualgo.kien.application.exception.ForbiddenException;
import com.qualgo.kien.application.query.ChannelMessagesQuery;
import com.qualgo.kien.application.query.ChannelsQuery;
import com.qualgo.kien.domain.entity.ChannelMessage;
import com.qualgo.kien.domain.entity.ChatChannel;
import com.qualgo.kien.infrastructure.web.controller.request.CreateChannelRequest;
import com.qualgo.kien.infrastructure.web.controller.request.SendMessageRequest;
import com.qualgo.kien.infrastructure.web.controller.response.PageResponse;
import com.qualgo.kien.infrastructure.web.filter.MyUserDetail;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/channels")
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
public class ChannelController {

  final QueryGateway queryGateway;

  final CommandGateway commandGateway;

  @PostMapping("/create")
  public Long createChannel(@RequestBody CreateChannelRequest request) {
    MyUserDetail userDetail = getUserDetail();
    return commandGateway.sendAndWait(
        CreateChannelCommand.builder()
            .userId(userDetail.getUserId())
            .channelName(request.getChannelName())
            .build());
  }

  private static MyUserDetail getUserDetail() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication == null
        || authentication.getPrincipal() == null
        || !(authentication.getPrincipal() instanceof MyUserDetail userDetail)) {
      throw new ForbiddenException();
    }
    return userDetail;
  }

  @GetMapping("")
  public List<ChatChannel> channels() throws ExecutionException, InterruptedException {
    List<ChatChannel> chatChannels =
        queryGateway
            .query(
                ChannelsQuery.builder().build(),
                ResponseTypes.multipleInstancesOf(ChatChannel.class))
            .get();
    log.info("Channel query");
    return chatChannels;
  }

  @PostMapping("/{channelId}/send")
  public Long sendMessage(@PathVariable Long channelId, @RequestBody SendMessageRequest request) {
    MyUserDetail userDetail = getUserDetail();
    return commandGateway.sendAndWait(
        SendMessageCommand.builder()
            .channelId(channelId)
            .userId(userDetail.getUserId())
            .contentType(request.getContentType())
            .content(request.getContent())
            .build());
  }

  @GetMapping("/{channelId}/messages")
  public PageResponse<ChannelMessage> getMessages(
      @PathVariable Long channelId, @RequestParam Optional<String> pageToken)
      throws ExecutionException, InterruptedException {
    Long lastId = null;
    if (pageToken.isPresent()) {
      lastId = Long.parseLong(pageToken.get());
    }

    PageChannelMessage channelMessages =
        queryGateway
            .query(
                ChannelMessagesQuery.builder()
                    .channelId(channelId)
                    .lastChannelMessageId(lastId)
                    .build(),
                PageChannelMessage.class)
            .get();
    return PageResponse.<ChannelMessage>builder()
        .data(channelMessages.getMessages())
        .pageSize(channelMessages.getPageSize())
        .nextPageToken(channelMessages.nextPageToken())
        .build();
  }

  @DeleteMapping("/{channelId}/messages/delete/{messageId}")
  public Long deleteMessage(@PathVariable Long channelId, @PathVariable Long messageId) {
    MyUserDetail userDetail = getUserDetail();
    return commandGateway.sendAndWait(
        DeleteMessageCommand.builder()
            .channelId(channelId)
            .userId(userDetail.getUserId())
            .channelId(channelId)
            .messageId(messageId)
            .build());
  }
}
