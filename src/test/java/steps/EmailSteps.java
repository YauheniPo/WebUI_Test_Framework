package steps;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import demo.test.forms.AuthorizeEmailForm;
import demo.test.pages.EmailAccountPage;

public class EmailSteps extends BaseSteps {
    private AuthorizeEmailForm authorizeEmailForm;
    private EmailAccountPage emailAccountPage;

    @Then("^Authorize Email Form is opened$")
    public void authorizeEmailForm() {
        authorizeEmailForm = new AuthorizeEmailForm();
    }

    @When("^I was authorized as '(.*)'$")
    public void authorized(String account) {
        authorizeEmailForm.signIn((String) SCENARIO_CONTEXT.getContextObj(account + "MailLogin"),
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
        demo.test.models.Mail mail = emailAccountPage.getMailsForm().choiceLastMail().getMail();
        SCENARIO_CONTEXT.setContext(account + "Mail", mail);
    }
}