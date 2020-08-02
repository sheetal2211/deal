package org.dfm.deal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.dfm.deal.domain.DealDomain;
import org.dfm.deal.domain.exception.DealNotFoundException;
import org.dfm.deal.domain.model.Deal;
import org.dfm.deal.domain.model.DealInfo;
import org.dfm.deal.domain.port.ObtainDeal;
import org.dfm.deal.domain.port.RequestDeal;

@ExtendWith(MockitoExtension.class)
public class AcceptanceTest {

  @Test
  @DisplayName("should be able to get deals when asked for deals from hard coded deals")
  public void getDealsFromHardCoded() {
  /*
      RequestDeal    - left side port
      DealDomain     - hexagon (domain)
      ObtainDeal     - right side port
   */
    RequestDeal requestDeal = new DealDomain(); // the deal is hard coded
    DealInfo dealInfo = requestDeal.getDeals();
    assertThat(dealInfo).isNotNull();
    assertThat(dealInfo.getDeals()).isNotEmpty().extracting("description")
        .contains(
            "If you could read a leaf or tree\r\nyoud have no need of books.\r\n-- Alistair Cockburn (1987)");
  }

  @Test
  @DisplayName("should be able to get deals when asked for deals from stub")
  public void getDealsFromMockedStub(@Mock ObtainDeal obtainDeal) {
    // Stub
    Deal deal = Deal.builder().code(1L).description(
        "I want to sleep\r\nSwat the flies\r\nSoftly, please.\r\n\r\n-- Masaoka Shiki (1867-1902)")
        .build();
    Mockito.lenient().when(obtainDeal.getAllDeals()).thenReturn(List.of(deal));
    // hexagon
    RequestDeal requestDeal = new DealDomain(obtainDeal);
    DealInfo dealInfo = requestDeal.getDeals();
    assertThat(dealInfo).isNotNull();
    assertThat(dealInfo.getDeals()).isNotEmpty().extracting("description")
        .contains(
            "I want to sleep\r\nSwat the flies\r\nSoftly, please.\r\n\r\n-- Masaoka Shiki (1867-1902)");
  }

  @Test
  @DisplayName("should be able to get deal when asked for deal by id from stub")
  public void getDealByIdFromMockedStub(@Mock ObtainDeal obtainDeal) {
    // Given
    // Stub
    Long code = 1L;
    String description = "I want to sleep\\r\\nSwat the flies\\r\\nSoftly, please.\\r\\n\\r\\n-- Masaoka Shiki (1867-1902)";
    Deal expectedDeal = Deal.builder().code(code).description(description).build();
    Mockito.lenient().when(obtainDeal.getDealByCode(code))
        .thenReturn(Optional.of(expectedDeal));
    // When
    RequestDeal requestDeal = new DealDomain(obtainDeal);
    Deal actualDeal = requestDeal.getDealByCode(code);
    assertThat(actualDeal).isNotNull().isEqualTo(expectedDeal);
  }

  @Test
  @DisplayName("should throw exception when asked for deal by id that does not exists from stub")
  public void getExceptionWhenAskedDealByIdThatDoesNotExist(@Mock ObtainDeal obtainDeal) {
    // Given
    // Stub
    Long code = -1000L;
    Mockito.lenient().when(obtainDeal.getDealByCode(code)).thenReturn(Optional.empty());
    // When
    RequestDeal requestDeal = new DealDomain(obtainDeal);
    // Then
    assertThatThrownBy(() -> requestDeal.getDealByCode(code)).isInstanceOf(
        DealNotFoundException.class)
        .hasMessageContaining("Deal with code " + code + " does not exist");
  }
}
