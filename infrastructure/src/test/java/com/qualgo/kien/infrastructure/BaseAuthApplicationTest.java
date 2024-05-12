package com.qualgo.kien.infrastructure;

import com.qualgo.kien.infrastructure.web.config.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;

public class BaseAuthApplicationTest extends BaseApplicationTest {

  @Autowired JwtService jwtService;

  protected String authToken;

  @BeforeEach
  public void getToken() {
    authToken = jwtService.generateToken("testUser");
  }
}
