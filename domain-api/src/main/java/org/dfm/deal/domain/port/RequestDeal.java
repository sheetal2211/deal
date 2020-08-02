package org.dfm.deal.domain.port;

import org.dfm.deal.domain.model.Deal;
import org.dfm.deal.domain.model.DealInfo;

public interface RequestDeal {

  DealInfo getDeals();
  Deal getDealByCode(Long code);
}
