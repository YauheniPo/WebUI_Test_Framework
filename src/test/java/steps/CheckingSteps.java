package steps;

import cucumber.api.java.en.And;
import demo.test.models.Mail;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class CheckingSteps extends BaseSteps {

    @And("^I checked letters data$")
    public void checkedLettersData() {
        List<Mail> mails = new LinkedList<>(Arrays.asList((Mail) scenarioContext.getContextObj("senderMail"),
                                                          (Mail) scenarioContext.getContextObj("recipientMail")));
        Mail apiMail = (Mail) scenarioContext.getContextObj("apiMail");
        for (Mail mail : mails) {
            assertWrapper.assertEquals(apiMail, mail);
            LOGGER.info("Expected Mail: '" + apiMail.toString() + "' same as Mail: '" + mail.toString() + "'");
        }
    }
}