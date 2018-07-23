package steps;

import webdriver.Logger;
import webdriver.ScenarioContext;
import webdriver.common.SoftAssertWrapper;

/**
 * The type Base steps.
 */
public class BaseSteps {
    static ScenarioContext scenarioContext = ScenarioContext.getInstance();
    static final Logger LOGGER = Logger.getInstance();
    static SoftAssertWrapper assertWrapper = SoftAssertWrapper.getInstance();
}