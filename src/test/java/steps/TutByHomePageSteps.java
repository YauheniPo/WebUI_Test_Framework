package steps;

import cucumber.api.java.en.Given;
import demo.test.pages.TutByHomePage;

public class TutByHomePageSteps {
    private String emailText;

    @Given("^url is opened '(.*)'$")
    public void testIsOpened(String s) {
        System.out.println(s);
        TutByHomePage tutByHomePage = new TutByHomePage();
    }
}