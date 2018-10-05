package steps;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import webdriver.BaseEntity;
import webdriver.common.ScenarioContext;

public class BeforeAndAfterSteps extends BaseEntity {

    @Before(order = 1)
    public void beforeScenario() {
        before();
    }

    @After(order = 1)
    public void afterScenario(Scenario scenario) {
        ScenarioContext.getInstance().clearScenario();
    }
}