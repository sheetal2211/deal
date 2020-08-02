package org.dfm.deal.domain;

import java.util.Optional;
import org.dfm.deal.domain.exception.DealNotFoundException;
import org.dfm.deal.domain.model.Deal;
import org.dfm.deal.domain.model.DealInfo;
import org.dfm.deal.domain.port.ObtainDeal;
import org.dfm.deal.domain.port.RequestDeal;

public class DealDomain implements RequestDeal {

  private ObtainDeal obtainDeal;

  public DealDomain() {
    this(new ObtainDeal() {
    });
  }

  public DealDomain(ObtainDeal obtainDeal) {
    this.obtainDeal = obtainDeal;
  }

  @Override
  public DealInfo getDeals() {
    return DealInfo.builder().deals(obtainDeal.getAllDeals()).build();
  }

  @Override
  public Deal getDealByCode(Long code) {
    Optional<Deal> deal = obtainDeal.getDealByCode(code);
    return deal.orElseThrow(() -> new DealNotFoundException(code));
  }
}
