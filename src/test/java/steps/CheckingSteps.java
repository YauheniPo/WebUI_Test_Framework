package steps;

import cucumber.api.java.en.And;
import demo.test.models.Mail;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class CheckingSteps extends BaseSteps {

    @And("^I checked letters data$")
    public void checkedLettersData() {
        List<Mail> mails = new LinkedList<>(Arrays.asList((Mail) SCENARIO_CONTEXT.getContextObj("senderMail"),
                                                          (Mail) SCENARIO_CONTEXT.getContextObj("recipientMail")));
        Mail apiMail = (Mail) SCENARIO_CONTEXT.getContextObj("apiMail");
        mails.forEach(mail -> {
            ASSERT_WRAPPER.assertEquals(apiMail, mail);
            LOGGER.info("Expected Mail: '" + apiMail.toString() + "' same as Mail: '" + mail.toString() + "'");
        });
    }
}