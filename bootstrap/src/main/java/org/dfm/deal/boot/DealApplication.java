package org.dfm.deal.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "org.dfm.deal")
public class DealApplication {

  public static void main(String[] args) {
    SpringApplication.run(DealApplication.class, args);
  }
}
