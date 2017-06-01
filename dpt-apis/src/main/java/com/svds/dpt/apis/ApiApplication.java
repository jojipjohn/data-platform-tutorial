package com.svds.dpt.apis;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

// run this way:
// $ mvn clean package && java -jar target/dpt-apis-1.0.0.jar

@EnableScheduling
@SpringBootApplication
public class ApiApplication {
  public static void main(String[] args) {
    SpringApplication.run(ApiApplication.class, args);
  }
}
