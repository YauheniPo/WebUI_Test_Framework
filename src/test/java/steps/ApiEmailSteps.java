package steps;

import cucumber.api.java.After;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import demo.test.testModels.Mail;
import demo.test.testModels.TestDataMails;
import demo.test.utils.FactoryInitParams;
import webdriver.Browser;
import webdriver.utils.mail.MailUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;
import java.util.function.Predicate;

public class ApiEmailSteps extends BaseSteps {
    private final String currentBrowser = Browser.getBrowserName();
    private static final ArrayList<MailUtils> mailBox = new ArrayList<>();
    private Predicate<Object> elIsNull = Objects::nonNull;

    private MailUtils getMailStore(String login, String password) {
        MailUtils mail = new MailUtils(login, password);
        mailBox.add(mail);
        return mail;
    }

    @After(order = 2)
    public void clearEmailAndCloseMailStore() {
        try {
            deleteMails();
            mailBox.forEach(mail -> {
                if (elIsNull.test(mail)) {
                    mail.closeStore();
                }
            });
        } finally {
            mailBox.clear();
        }
    }

    @Given("^test data from \"([^\"]*)\"$")
    public void testDataFrom(String datafile) {
        TestDataMails testDataMails = (TestDataMails) (new FactoryInitParams(datafile).getTestData()[0]);
        SCENARIO_CONTEXT.setContext("senderMailLogin", testDataMails.getSenderMailLogin());
        SCENARIO_CONTEXT.setContext("senderMailPassword", testDataMails.getSenderMailPassword());
        SCENARIO_CONTEXT.setContext("recipientMailLogin", testDataMails.getRecipientMailLogin());
        SCENARIO_CONTEXT.setContext("recipientMailPassword", testDataMails.getRecipientMailPassword());
    }

    @And("^deleting email data$")
    public void deleteMails() {
        mailBox.forEach(mail -> {if(elIsNull.test(mail)) {mail.deleteAllMessages();}});
    }

    @And("^connecting '(.*)' email store$")
    public void connectingEmailStore(String account) {
        MailUtils email = getMailStore((String) SCENARIO_CONTEXT.getContextObj(account + "MailLogin"),
                (String) SCENARIO_CONTEXT.getContextObj(account + "MailPassword"));
        SCENARIO_CONTEXT.setContext(account, email);
    }

    @And("^creating letter with text '(.*)'$")
    public void generationLetter(String textLetter) {
        String dateTimeMail = new SimpleDateFormat("HH:mm").format(new Date());
        String subject = String.format("From %s", SCENARIO_CONTEXT.getContextObj("senderMailLogin"));
        String text = String.format("%s_%s_%s", textLetter, currentBrowser, dateTimeMail);
        Mail apiMail = new Mail(subject, text, (String) SCENARIO_CONTEXT.getContextObj("senderMailLogin"),
                (String) SCENARIO_CONTEXT.getContextObj("recipientMailLogin"));
        SCENARIO_CONTEXT.setContext("apiMail", apiMail);
    }

    @And("^sending letter to recipient$")
    public void sendingLetterToRecipient() {
        Mail apiMail = (Mail) SCENARIO_CONTEXT.getContextObj("apiMail");
        ((MailUtils) SCENARIO_CONTEXT.getContextObj("sender")).messageGeneration(apiMail.getText(),
                                                                                 apiMail.getSubject(), apiMail.getToAddress()).sendMail();
    }

    @And("^sending letter in sender send folder$")
    public void sendingLetterInSendFolder() {
        ((MailUtils) SCENARIO_CONTEXT.getContextObj("sender")).addMsgInSentFolder();
    }
}