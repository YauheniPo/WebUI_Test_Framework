package steps;

import cucumber.api.java.en.When;
import webdriver.Browser;

public class CommonSteps extends BaseSteps {

    @When("^I opened starting Home Page$")
    public void openStartingHomePage() {
        Browser.openStartPage();
    }
}