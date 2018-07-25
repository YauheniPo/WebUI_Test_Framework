package steps;

import webdriver.Logger;
import webdriver.asserts.AssertWrapper;
import webdriver.common.ScenarioContext;

class BaseSteps {
    static final ScenarioContext SCENARIO_CONTEXT = ScenarioContext.getInstance();
    static final Logger LOGGER = Logger.getInstance();
    static final AssertWrapper ASSERT_WRAPPER = AssertWrapper.getInstance();
}