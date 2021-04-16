package com.run.holiday;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Log4j2
@SpringBootApplication
@OpenAPIDefinition(
    info =
        @Info(
            title = "Holiday Service API",
            version = "v1",
            contact = @Contact(name = "RuN", email = "ry@powerledger.io")))
public class HolidayServiceApplication {

  public static void main(String[] args) {
    SpringApplication.run(HolidayServiceApplication.class, args);
  }
}
