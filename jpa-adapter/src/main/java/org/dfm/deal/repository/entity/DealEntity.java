package org.dfm.deal.repository.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.dfm.deal.domain.model.Deal;

@Table(name = "T_DEAL")
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DealEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_DEAL")
  @Column(name = "TECH_ID")
  private Long techId;

  @Column(name = "CODE")
  private Long code;

  @Column(name = "DESCRIPTION")
  private String description;

  public Deal toModel() {
    return Deal.builder().code(code).description(description).build();
  }
}
