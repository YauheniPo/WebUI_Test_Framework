package steps;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import webdriver.Browser;

public class CommonSteps extends BaseSteps {

    @When("^I opened starting Home Page$")
    public void openStartingHomePage() {
        Browser.openStartPage();
    }

    @Given("^sending email letter of test data from the \"([^\"]*)\" with text '(.*)'$")
    public void sendingEmailLetterOfTestDataFromThe(String dataFileName, String textLetter) {
        ApiEmailSteps apiEmailSteps = new ApiEmailSteps();
        apiEmailSteps.testDataFrom(dataFileName);
        sendingEmailLetter(apiEmailSteps, textLetter);
    }

    @Given("^sending email letter by test data from file and with text '(.*)'$")
    public void sendingEmailLetterOfTestDataFromThe(String textLetter) {
        ApiEmailSteps apiEmailSteps = new ApiEmailSteps();
        sendingEmailLetter(apiEmailSteps, textLetter);
    }

    @When("^the '(.*)' logged in$")
    public void logIn(String account) {
        HeaderSteps headerSteps = new HeaderSteps();
        headerSteps.clickHeaderLabel("email");
        EmailSteps emailSteps = new EmailSteps();
        emailSteps.authorizeEmailForm();
        emailSteps.authorization(account);
        CheckingSteps checkingSteps = new CheckingSteps();
        checkingSteps.userAuthorized(account);
    }

    @Then("^getting a data of the last letter from the '(.*)' folder of '(.*)'$")
    public void gettingDataOfTheLastLetterFromFolder(String folderName, String account) {
        EmailSteps emailSteps = new EmailSteps();
        emailSteps.emailPageIsOpened();
        emailSteps.clickFolder(folderName);
        emailSteps.choiceLastLetter(account);
    }

    @And("^'(.*)' doing logout$")
    public void logout(String account) {
        HeaderSteps headerSteps = new HeaderSteps();
        headerSteps.clickAccountDropdownLabel("logout");
        CheckingSteps checkingSteps = new CheckingSteps();
        checkingSteps.userNotAuthorized(account);
        openStartingHomePage();
        new TutByHomePageSteps().homePageIsOpened();
    }

    private void sendingEmailLetter(ApiEmailSteps apiEmailSteps, String textLetter) {
        apiEmailSteps.connectingEmailStore("sender");
        apiEmailSteps.connectingEmailStore("recipient");
        apiEmailSteps.deleteMails();
        apiEmailSteps.generationLetter(textLetter);
        apiEmailSteps.sendingLetterToRecipient();
        apiEmailSteps.sendingLetterInSendFolder();
    }
}