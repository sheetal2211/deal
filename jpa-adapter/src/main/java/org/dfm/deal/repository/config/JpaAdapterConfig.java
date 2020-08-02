package org.dfm.deal.repository.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.dfm.deal.domain.port.ObtainDeal;
import org.dfm.deal.repository.DealRepository;
import org.dfm.deal.repository.dao.DealDao;

@Configuration
@EntityScan("org.dfm.deal.repository.entity")
@EnableJpaRepositories("org.dfm.deal.repository.dao")
public class JpaAdapterConfig {

  @Bean
  public ObtainDeal getDealRepository(DealDao dealDao) {
    return new DealRepository(dealDao);
  }
}
