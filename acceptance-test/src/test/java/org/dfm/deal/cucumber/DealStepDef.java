package org.dfm.deal.cucumber;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import io.cucumber.datatable.DataTable;
import io.cucumber.java8.En;
import io.cucumber.java8.HookNoArgsBody;
import io.cucumber.spring.CucumberContextConfiguration;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.dfm.deal.DealE2EApplication;
import org.dfm.deal.domain.model.Deal;
import org.dfm.deal.domain.model.DealInfo;
import org.dfm.deal.repository.dao.DealDao;
import org.dfm.deal.repository.entity.DealEntity;
import org.dfm.deal.rest.exception.DealExceptionResponse;


@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = DealE2EApplication.class, webEnvironment = RANDOM_PORT)
@CucumberContextConfiguration
public class DealStepDef implements En {

  private static final String LOCALHOST = "http://localhost:";
  private static final String API_URI = "/api/v1/deals";
  @LocalServerPort
  private int port;
  private ResponseEntity responseEntity;

  public DealStepDef(TestRestTemplate restTemplate, DealDao dealDao) {

    DataTableType(
        (Map<String, String> row) -> Deal.builder().code(Long.parseLong(row.get("code")))
            .description(row.get("description")).build());
    DataTableType(
        (Map<String, String> row) -> DealEntity.builder().code(Long.parseLong(row.get("code")))
            .description(row.get("description"))
            .build());

    Before((HookNoArgsBody) dealDao::deleteAll);
    After((HookNoArgsBody) dealDao::deleteAll);

    Given("the following deals exists in the library", (DataTable dataTable) -> {
      List<DealEntity> poems = dataTable.asList(DealEntity.class);
      dealDao.saveAll(poems);
    });

    When("user requests for all deals", () -> {
      String url = LOCALHOST + port + API_URI;
      responseEntity = restTemplate.getForEntity(url, DealInfo.class);
    });

    When("user requests for deals by code {string}", (String code) -> {
      String url = LOCALHOST + port + API_URI + "/" + code;
      responseEntity = restTemplate.getForEntity(url, Deal.class);
    });

    When("user requests for deals by id {string} that does not exists", (String code) -> {
      String url = LOCALHOST + port + API_URI + "/" + code;
      responseEntity = restTemplate.getForEntity(url, DealExceptionResponse.class);
    });

    Then("the user gets an exception {string}", (String exception) -> {
      assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
      Object body = responseEntity.getBody();
      assertThat(body).isNotNull();
      assertThat(body).isInstanceOf(DealExceptionResponse.class);
      assertThat(((DealExceptionResponse) body).getMessage()).isEqualTo(exception);
    });

    Then("the user gets the following deals", (DataTable dataTable) -> {
      List<Deal> expectedDeals = dataTable.asList(Deal.class);
      assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
      Object body = responseEntity.getBody();
      assertThat(body).isNotNull();
      if (body instanceof DealInfo) {
        assertThat(((DealInfo) body).getDeals()).isNotEmpty().extracting("description")
            .containsAll(expectedDeals.stream().map(Deal::getDescription)
                .collect(Collectors.toList()));
      } else if (body instanceof Deal) {
        assertThat(body).isNotNull().extracting("description")
            .isEqualTo(expectedDeals.get(0).getDescription());
      }
    });
  }
}


