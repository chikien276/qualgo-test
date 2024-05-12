package com.qualgo.kien.infrastructure.web.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.type.TypeReference;
import com.qualgo.kien.domain.entity.ChannelMessage;
import com.qualgo.kien.domain.entity.ChatChannel;
import com.qualgo.kien.domain.entity.repository.ChannelMessageRepository;
import com.qualgo.kien.infrastructure.BaseAuthApplicationTest;
import com.qualgo.kien.infrastructure.web.controller.request.CreateChannelRequest;
import com.qualgo.kien.infrastructure.web.controller.request.SendMessageRequest;
import com.qualgo.kien.infrastructure.web.controller.response.PageResponse;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

class ChannelControllerTest extends BaseAuthApplicationTest {
  Long channelId = null;

  @Autowired
  ChannelMessageRepository channelMessageRepository;

  @Test
  void createChannel() throws Exception {
    MvcResult testChannelName =
        this.mockMvc
            .perform(
                MockMvcRequestBuilders.post("/channels/create")
                    .header("Authorization", "Bearer " + authToken)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(
                        mapper.writeValueAsBytes(
                            CreateChannelRequest.builder()
                                .channelName("Test Channel Name")
                                .build())))
            .andExpect(status().isOk())
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andReturn();
    Long channelId = Long.parseLong(testChannelName.getResponse().getContentAsString());
    Assertions.assertNotNull(channelId);
    this.channelId = channelId;
  }

  @Test
  void channels() throws Exception {
    createChannel();
    MvcResult channelsResult =
        this.mockMvc
            .perform(
                MockMvcRequestBuilders.get("/channels")
                    .header("Authorization", "Bearer " + authToken)
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andReturn();

    List<ChatChannel> chatChannels =
        mapper.readValue(
            channelsResult.getResponse().getContentAsByteArray(), new TypeReference<>() {});
    Assertions.assertNotNull(chatChannels);
    Assertions.assertFalse(chatChannels.isEmpty());
  }

  @Test
  void sendMessage() throws Exception {
    createChannel();
    sendSingleMessage();
  }

  private void sendSingleMessage() throws Exception {
    MvcResult channelsResult =
        this.mockMvc
            .perform(
                MockMvcRequestBuilders.post("/channels/" + channelId + "/send")
                    .header("Authorization", "Bearer " + authToken)
                    .contentType(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(
                        mapper.writeValueAsBytes(
                            SendMessageRequest.builder()
                                .content("This is a message")
                                .contentType(MediaType.TEXT_PLAIN.getType())
                                .build())))
            .andExpect(status().isOk())
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andReturn();
  }

  @Test
  void getMessages() throws Exception {
    createChannel();
    for (int i = 0; i < 20; i++) {
      sendSingleMessage();
    }
    PageResponse<ChannelMessage> responses = getChannelMessages(null);
    MvcResult channelsResult;
    Assertions.assertNotNull(responses.getData());
    Assertions.assertNotNull(responses.getNextPageToken());
    responses = getChannelMessages(responses.getNextPageToken());
    Assertions.assertNotNull(responses.getData());
    Assertions.assertFalse(responses.getData().isEmpty());
  }

  private PageResponse<ChannelMessage> getChannelMessages(String nextPageToken) throws Exception {
    MockHttpServletRequestBuilder request =
        MockMvcRequestBuilders.get("/channels/" + channelId + "/messages")
            .header("Authorization", "Bearer " + authToken)
            .contentType(MediaType.APPLICATION_JSON);
    if (nextPageToken != null) {
      request.param("pageToken", nextPageToken);
    }
    MvcResult channelsResult =
        this.mockMvc
            .perform(request)
            .andExpect(status().isOk())
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andReturn();
    PageResponse<ChannelMessage> responses =
        mapper.readValue(
            channelsResult.getResponse().getContentAsByteArray(), new TypeReference<>() {});
    return responses;
  }

  @Test
  void deleteMessage() throws Exception {
    createChannel();
    sendSingleMessage();
    PageResponse<ChannelMessage> channelMessages = getChannelMessages(null);
    Optional<ChannelMessage> firstMessage = channelMessages.getData().stream().findFirst();
    Assertions.assertTrue(firstMessage.isPresent());
    ChannelMessage channelMessage = firstMessage.get();
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.delete(
                    "/channels/" + channelId + "/messages/" + channelMessage.getId())
                .header("Authorization", "Bearer " + authToken)
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andReturn();

    ChannelMessage message = channelMessageRepository.findById(channelMessage.getId());
    Assertions.assertNull(message);
  }
}
