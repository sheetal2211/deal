package org.dfm.deal.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.dfm.deal.domain.model.Deal;
import org.dfm.deal.domain.model.DealInfo;
import org.dfm.deal.domain.port.RequestDeal;

@RestController
@RequestMapping("/api/v1/deals")
public class DealResource {

  private RequestDeal requestDeal;

  public DealResource(RequestDeal requestDeal) {
    this.requestDeal = requestDeal;
  }

  @GetMapping
  public ResponseEntity<DealInfo> getDeals() {
    return ResponseEntity.ok(requestDeal.getDeals());
  }

  @GetMapping("/{code}")
  public ResponseEntity<Deal> getDealByCode(@PathVariable Long code) {
    return ResponseEntity.ok(requestDeal.getDealByCode(code));
  }
}
