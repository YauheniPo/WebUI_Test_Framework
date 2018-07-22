package steps;

import cucumber.api.java.en.Given;
import demo.test.pages.TutByHomePage;

public class TutByHomePageSteps extends BaseSteps {

    @Given("^tut.by Home Page is opened$")
    public void homePageIsOpened() {
        TutByHomePage tutByHomePage = new TutByHomePage();
    }
}