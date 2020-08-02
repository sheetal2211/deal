package org.dfm.deal.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.dfm.deal.domain.exception.DealNotFoundException;
import org.dfm.deal.domain.model.Deal;
import org.dfm.deal.domain.model.DealInfo;
import org.dfm.deal.domain.port.RequestDeal;
import org.dfm.deal.rest.exception.DealExceptionResponse;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = DealRestAdapterApplication.class, webEnvironment = RANDOM_PORT)
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
public class DealResourceTest {

  private static final String LOCALHOST = "http://localhost:";
  private static final String API_URI = "/api/v1/deals";
  @LocalServerPort
  private int port;
  @Autowired
  private TestRestTemplate restTemplate;
  @Autowired
  private RequestDeal requestDeal;

  @Test
  @DisplayName("should start the rest adapter application")
  public void startup() {
    assertThat(Boolean.TRUE).isTrue();
  }

  @Test
  @DisplayName("should give deals when asked for deals with the support of domain stub")
  public void obtainDealsFromDomainStub() {
    // Given
    Deal deal = Deal.builder().code(1L).description("Johnny Johnny Yes Papa !!").build();
    Mockito.lenient().when(requestDeal.getDeals())
        .thenReturn(DealInfo.builder().deals(List.of(deal)).build());
    // When
    String url = LOCALHOST + port + API_URI;
    ResponseEntity<DealInfo> responseEntity = restTemplate.getForEntity(url, DealInfo.class);
    // Then
    assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(responseEntity.getBody()).isNotNull();
    assertThat(responseEntity.getBody().getDeals()).isNotEmpty().extracting("description")
        .contains("Johnny Johnny Yes Papa !!");
  }

  @Test
  @DisplayName("should give the deal when asked for an deal by code with the support of domain stub")
  public void obtainDealByCodeFromDomainStub() {
    // Given
    Long code = 1L;
    String description = "Johnny Johnny Yes Papa !!";
    Deal deal = Deal.builder().code(code).description(description).build();
    Mockito.lenient().when(requestDeal.getDealByCode(code)).thenReturn(deal);
    // When
    String url = LOCALHOST + port + API_URI + "/" + code;
    ResponseEntity<Deal> responseEntity = restTemplate.getForEntity(url, Deal.class);
    // Then
    assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(responseEntity.getBody()).isNotNull();
    assertThat(responseEntity.getBody()).isEqualTo(deal);
  }

  @Test
  @DisplayName("should give exception when asked for an deal by code that does not exists with the support of domain stub")
  public void shouldGiveExceptionWhenAskedForAnDealByCodeFromDomainStub() {
    // Given
    Long code = -1000L;
    Mockito.lenient().when(requestDeal.getDealByCode(code)).thenThrow(new
        DealNotFoundException(code));
    // When
    String url = LOCALHOST + port + API_URI + "/" + code;
    ResponseEntity<DealExceptionResponse> responseEntity = restTemplate
        .getForEntity(url, DealExceptionResponse.class);
    // Then
    assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    assertThat(responseEntity.getBody()).isNotNull();
    assertThat(responseEntity.getBody()).isEqualTo(DealExceptionResponse.builder()
        .message("Deal with code " + code + " does not exist").path(API_URI + "/" + code)
        .build());
  }
}
