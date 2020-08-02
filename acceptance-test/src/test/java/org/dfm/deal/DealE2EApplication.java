package org.dfm.deal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.dfm.deal.domain.DealDomain;
import org.dfm.deal.domain.port.ObtainDeal;
import org.dfm.deal.domain.port.RequestDeal;
import org.dfm.deal.repository.config.JpaAdapterConfig;

@SpringBootApplication
public class DealE2EApplication {

  public static void main(String[] args) {
    SpringApplication.run(DealE2EApplication.class);
  }

  @TestConfiguration
  @Import(JpaAdapterConfig.class)
  static class DealConfig {

    @Bean
    public RequestDeal getRequestDeal(final ObtainDeal obtainDeal) {
      return new DealDomain(obtainDeal);
    }
  }
}
