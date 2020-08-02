package org.dfm.deal.repository;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.dfm.deal.domain.port.ObtainDeal;
import org.dfm.deal.repository.dao.DealDao;

@SpringBootApplication
public class DealJpaAdapterApplication {

  public static void main(String[] args) {
    SpringApplication.run(DealJpaAdapterApplication.class, args);
  }

  @TestConfiguration
  static class DealJpaTestConfig {

    @Bean
    public ObtainDeal getObtainDealRepository(DealDao dealDao) {
      return new DealRepository(dealDao);
    }
  }
}
