package org.dfm.deal.boot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.dfm.deal.domain.DealDomain;
import org.dfm.deal.domain.port.ObtainDeal;
import org.dfm.deal.domain.port.RequestDeal;
import org.dfm.deal.repository.config.JpaAdapterConfig;

@Configuration
@Import(JpaAdapterConfig.class)
public class BootstrapConfig {

  @Bean
  public RequestDeal getRequestDeal(ObtainDeal obtainDeal) {
    return new DealDomain(obtainDeal);
  }
}
