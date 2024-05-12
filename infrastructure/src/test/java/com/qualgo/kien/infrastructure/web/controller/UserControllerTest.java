package com.qualgo.kien.infrastructure.web.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.qualgo.kien.infrastructure.BaseApplicationTest;
import com.qualgo.kien.infrastructure.web.controller.request.LoginRequest;
import com.qualgo.kien.infrastructure.web.controller.request.RegisterRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

class UserControllerTest extends BaseApplicationTest {

  @Test
  void shouldReturnForbidden() throws Exception {
    this.mockMvc.perform(MockMvcRequestBuilders.get("/")).andExpect(status().is4xxClientError());
  }

  @Test
  void shouldRegisterSuccess() throws Exception {
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    mapper.writeValueAsBytes(
                        RegisterRequest.builder()
                            .email("Kien@email.com")
                            .password("123ABCcde")
                            .username("kien")
                            .build())))
        .andExpect(status().isOk());
  }

  @Test
  void shouldReturnDuplicate() throws Exception {
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    mapper.writeValueAsBytes(
                        RegisterRequest.builder()
                            .email("Kien1@email.com")
                            .password("123ABCcde")
                            .username("kien1")
                            .build())))
        .andExpect(status().isOk());
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    mapper.writeValueAsBytes(
                        RegisterRequest.builder()
                            .email("Kien1@email.com")
                            .password("123ABCcde")
                            .username("kien1")
                            .build())))
        .andExpect(status().is4xxClientError());
  }

  @Test
  void passwordTooLong() throws Exception {
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    mapper.writeValueAsBytes(
                        RegisterRequest.builder()
                            .email("Kien1@email.com")
                            .password("12312312312312312312312312312312312312123123123123123")
                            .username("kien2")
                            .build())))
        .andExpect(status().is4xxClientError())
        .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
  }

  @Test
  void registerThenLogin() throws Exception {
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    mapper.writeValueAsBytes(
                        RegisterRequest.builder()
                            .email("Kien1@email.com")
                            .password("123456789")
                            .username("register")
                            .build())))
        .andExpect(status().isOk());
    MvcResult loginResult =
        this.mockMvc
            .perform(
                MockMvcRequestBuilders.post("/auth/login")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(
                        mapper.writeValueAsBytes(
                            LoginRequest.builder()
                                .username("register")
                                .password("123456789")
                                .build())))
            .andExpect(status().isOk())
            .andReturn();
    String token = loginResult.getResponse().getContentAsString();
    Assertions.assertNotNull(token);
  }
}
