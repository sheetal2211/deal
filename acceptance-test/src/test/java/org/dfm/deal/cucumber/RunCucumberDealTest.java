package org.dfm.deal.cucumber;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "classpath:features", plugin = {
    "json:target/cucumber/deal.json", "json:target/cucumber/deal.xml"}, tags =
    "@Deal", glue = "classpath:org.dfm.deal.cucumber")
public class RunCucumberDealTest {

}
