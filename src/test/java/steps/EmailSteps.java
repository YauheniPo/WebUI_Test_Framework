package steps;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import demo.test.webentities.pages.AuthorizeEmailPage;
import demo.test.webentities.pages.EmailAccountPage;
import demo.test.webentities.models.Mail;

public class EmailSteps extends BaseSteps {
    private AuthorizeEmailPage authorizeEmailPage;
    private EmailAccountPage emailAccountPage;

    @Then("^Authorize Email Form is opened$")
    public void authorizeEmailForm() {
        authorizeEmailPage = new AuthorizeEmailPage();
    }

    @When("^I was authorized as '(.*)'$")
    public void authorization(String account) {
        authorizeEmailPage.signIn((String) SCENARIO_CONTEXT.getContextObj(account + "MailLogin"),
                                  (String) SCENARIO_CONTEXT.getContextObj(account + "MailPassword"));
    }

    @Then("^Email Page is opened$")
    public void emailPageIsOpened() {
        emailAccountPage = new EmailAccountPage();
    }

    @When("^I did click to '(.*)' folder$")
    public void clickFolder(String folder) {
        emailAccountPage.fetchEmailFolder(EmailAccountPage.NavBox.valueOf(folder.toUpperCase()));
    }

    @And("^I chose '(.*)' last letter$")
    public void choiceLastLetter(String account) {
        Mail mail = emailAccountPage.getMailsForm().choiceLastMail().getMail();
        SCENARIO_CONTEXT.setContext(account + "Mail", mail);
    }
}