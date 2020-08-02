package org.dfm.deal.repository.dao;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.dfm.deal.repository.entity.DealEntity;

@Repository
public interface DealDao extends JpaRepository<DealEntity, Long> {

  Optional<DealEntity> findByCode(Long code);
}
