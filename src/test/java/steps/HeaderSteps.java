package steps;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import demo.test.forms.TutByHeader;
import demo.test.forms.AccountForm;
import demo.test.pages.EmailAccountPage;
import demo.test.pages.TutByHomePage;

public class HeaderSteps extends BaseSteps {

    @When("^I did click to '(.*)'$")
    public void clickHeaderLabel(String label) {
        new TutByHomePage().getHeader().clickTopBarElement(TutByHeader.TopBar.valueOf(label.toUpperCase()));
    }

    @Then("^I chose '(.*)' in account menu$")
    public void clickAccountDropdownLabel(String label) {
        new EmailAccountPage().getAccountForm().clickUserAccount().clickUserDropdownField(AccountForm.UserDropdown.valueOf(label.toUpperCase()));
    }
}