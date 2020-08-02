package org.dfm.deal.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.dfm.deal.domain.model.Deal;
import org.dfm.deal.domain.port.ObtainDeal;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class DealJpaTest {

  @Autowired
  private ObtainDeal obtainDeal;

  @Test
  @DisplayName("should start the application")
  public void startup() {
    assertThat(Boolean.TRUE).isTrue();
  }

  @Sql(scripts = {"/sql/data.sql"})
  @Test
  @DisplayName("given deals exist in database when asked should return all deals from database")
  public void shouldGiveMeDealsWhenAskedGivenDealExistsInDatabase() {
    // Given from @Sql
    // When
    List<Deal> deals = obtainDeal.getAllDeals();
    // Then
    assertThat(deals).isNotNull().extracting("description").contains("Twinkle twinkle little star");
  }

  @Test
  @DisplayName("given no deals exists in database when asked should return empty")
  public void shouldGiveNoDealWhenAskedGivenDealsDoNotExistInDatabase() {
    // When
    List<Deal> deals = obtainDeal.getAllDeals();
    // Then
    assertThat(deals).isNotNull().isEmpty();
  }

  @Sql(scripts = {"/sql/data.sql"})
  @Test
  @DisplayName("given deals exists in database when asked for deal by id should return the deal")
  public void shouldGiveTheDealWhenAskedByIdGivenThatDealByThatIdExistsInDatabase() {
    // Given from @Sql
    // When
    Optional<Deal> deal = obtainDeal.getDealByCode(1L);
    // Then
    assertThat(deal).isNotNull().isNotEmpty().get().isEqualTo(Deal.builder().code(1L).description("Twinkle twinkle little star").build());
  }

  @Sql(scripts = {"/sql/data.sql"})
  @Test
  @DisplayName("given deals exists in database when asked for deal by id that does not exist should give empty")
  public void shouldGiveNoDealWhenAskedByIdGivenThatDealByThatIdDoesNotExistInDatabase() {
    // Given from @Sql
    // When
    Optional<Deal> deal = obtainDeal.getDealByCode(-1000L);
    // Then
    assertThat(deal).isEmpty();
  }
}
