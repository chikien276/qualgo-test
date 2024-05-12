package com.qualgo.kien.infrastructure;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(
    scanBasePackages = {"com.qualgo.kien.application", "com.qualgo.kien.infrastructure"})
@MapperScan("com.qualgo.kien.infrastructure.persistent.mapper")
public class WebApplication {

  public static void main(String[] args) {
    SpringApplication.run(WebApplication.class, args);
  }
}
