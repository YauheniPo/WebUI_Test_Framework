package steps;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import webdriver.BaseEntity;
import webdriver.ScenarioContext;

/**
 * The type Before and after steps.
 */
public class BeforeAndAfterSteps extends BaseEntity {

    @Before(order = 1)
    public void beforeScenario() {
        before();
    }

    @After(order = 1)
    public void afterScenario() {
        after();
        ScenarioContext.getInstance().clearScenario();
    }
}