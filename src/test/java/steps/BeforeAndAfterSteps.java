package steps;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import webdriver.DriverEntity;
import webdriver.common.ScenarioContext;

public class BeforeAndAfterSteps extends DriverEntity {

    @Before(order = 1)
    public void beforeScenario() {
        before();
    }

    @After(order = 1)
    public void afterScenario() {
        ScenarioContext.getInstance().clearScenario();
    }
}