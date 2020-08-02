package org.dfm.deal.domain.port;

import java.util.List;
import java.util.Optional;
import org.dfm.deal.domain.model.Deal;

public interface ObtainDeal {

  default List<Deal> getAllDeals() {
    Deal deal = Deal.builder().code(1L).description(
        "If you could read a leaf or tree\r\nyoud have no need of books.\r\n-- Alistair Cockburn (1987)")
        .build();
    return List.of(deal);
  }

  default Optional<Deal> getDealByCode(Long code) {
    return Optional.of(Deal.builder().code(1L).description(
        "If you could read a leaf or tree\r\nyoud have no need of books.\r\n-- Alistair Cockburn (1987)")
        .build());
  }
}
