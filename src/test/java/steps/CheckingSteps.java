package steps;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import demo.test.forms.AccountForm;
import demo.test.testModels.Mail;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class CheckingSteps extends BaseSteps {

    @And("^checking data letters$")
    public void checkingDataLetters() {
        List<Mail> mails = new LinkedList<>(Arrays.asList((Mail) SCENARIO_CONTEXT.getContextObj("senderMail"),
                                                          (Mail) SCENARIO_CONTEXT.getContextObj("recipientMail")));
        Mail apiMail = (Mail) SCENARIO_CONTEXT.getContextObj("apiMail");
        mails.forEach(mail -> {
            ASSERT_WRAPPER.assertEquals(apiMail, mail);
            LOGGER.info("Expected Mail: '" + apiMail.toString() + "' same as Mail: '" + mail.toString() + "'");
        });
    }

    @Then("^'(.*)' authorized$")
    public void userAuthorized(String login) {
        checkAuthorization(login, Boolean.TRUE);
    }

    @Then("^'(.*)' not authorized$")
    public void userNotAuthorized(String login) {
        checkAuthorization(login, Boolean.FALSE);
    }

    private void checkAuthorization(String login, Boolean expectedResult) {
        LOGGER.info("Verify authorization");
        ASSERT_WRAPPER.assertEquals(new AccountForm().isAuthorized((String)SCENARIO_CONTEXT.getContextObj(login + "MailLogin")), expectedResult);
    }
}