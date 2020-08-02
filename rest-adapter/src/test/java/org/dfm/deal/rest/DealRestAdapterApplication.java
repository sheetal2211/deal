package org.dfm.deal.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.dfm.deal.domain.port.RequestDeal;

@SpringBootApplication
@ComponentScan(basePackages = "org.dfm.deal")
public class DealRestAdapterApplication {

  @MockBean
  private RequestDeal requestDeal;

  public static void main(String[] args) {
    SpringApplication.run(DealRestAdapterApplication.class, args);
  }
}
