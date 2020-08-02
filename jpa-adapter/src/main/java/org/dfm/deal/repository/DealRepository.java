package org.dfm.deal.repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.dfm.deal.domain.model.Deal;
import org.dfm.deal.domain.port.ObtainDeal;
import org.dfm.deal.repository.dao.DealDao;
import org.dfm.deal.repository.entity.DealEntity;

public class DealRepository implements ObtainDeal {

  private DealDao dealDao;

  public DealRepository(DealDao dealDao) {
    this.dealDao = dealDao;
  }

  @Override
  public List<Deal> getAllDeals() {
    return dealDao.findAll().stream().map(DealEntity::toModel).collect(Collectors.toList());
  }

  @Override
  public Optional<Deal> getDealByCode(Long code) {
    Optional<DealEntity> dealEntity = dealDao.findByCode(code);
    return dealEntity.map(DealEntity::toModel);
  }
}
