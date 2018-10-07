package steps;

import cucumber.api.java.en.Given;
import demo.test.webentities.pages.TutByHomePage;

public class TutByHomePageSteps extends BaseSteps {

    @Given("^tut.by Home Page is (?:opened|navigated)$")
    public void homePageIsOpened() {
        new TutByHomePage();
    }
}