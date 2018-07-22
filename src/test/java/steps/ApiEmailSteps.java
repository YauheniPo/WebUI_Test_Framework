package steps;

import cucumber.api.java.After;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import demo.test.models.Mail;
import demo.test.models.TestDataMails;
import demo.test.utils.FactoryInitParams;
import webdriver.Browser;
import webdriver.Logger;
import webdriver.utils.mail.MailUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ApiEmailSteps extends BaseSteps {
    private final String currentBrowser = Browser.getBrowserName();
    private final ArrayList<MailUtils> mailBox = new ArrayList<>();

    /**
     * Get params object [ ]
     *
     * @return the object [ ]
     */
    private Object[] getParams() {
        String dataBaseLocation = Browser.getPropsStage().getProperty("dataBaseLocation");
        Object[] dataProvider = new FactoryInitParams().getTestData(dataBaseLocation);
        if (dataProvider == null) {
            Logger.getInstance().error(String.format("Data not received from %s", dataBaseLocation));
        }
        return dataProvider;
    }

    /**
     * Gets mail store.
     *
     * @param login    the login
     * @param password the password
     *
     * @return the mail store
     */
    private MailUtils getMailStore(String login, String password) {
        MailUtils mail = new MailUtils(login, password);
        mailBox.add(mail);
        return mail;
    }

    /**
     * Clear email and close mail store.
     */
    @After(order = 2)
    public void clearEmailAndCloseMailStore() {
        deleteMails();
        for (MailUtils mail : mailBox) {
            if (mail != null) {
                mail.closeStore();
            }
        }
    }

    @Given("^test data$")
    public void getTestData() {
        TestDataMails testDataMails = (TestDataMails) (getParams()[0]);
        scenarioContext.setContext("senderMailLogin", testDataMails.getSenderMailLogin());
        scenarioContext.setContext("senderMailPassword", testDataMails.getSenderMailPassword());
        scenarioContext.setContext("recipientMailLogin", testDataMails.getRecipientMailLogin());
        scenarioContext.setContext("recipientMailPassword", testDataMails.getRecipientMailPassword());
    }

    @And("^deleting email data$")
    public void deleteMails() {
        for (MailUtils mail : mailBox) {
            if (mail != null) {
                mail.deleteAllMessages();
            }
        }
    }

    @And("^'(.*)' connecting email store$")
    public void connectingEmailStore(String account) {
        MailUtils email = getMailStore((String) scenarioContext.getContextObj(account + "MailLogin"),
                (String) scenarioContext.getContextObj(account + "MailPassword"));
        scenarioContext.setContext(account, email);
    }

    @And("^generation letter with text '(.*)'$")
    public void generationLetter(String emailText) {
        String dateTimeMail = new SimpleDateFormat("HH:mm").format(new Date());
        String subject = String.format("From %s", scenarioContext.getContextObj("senderMailLogin"));
        String text = String.format("%s_%s_%s", emailText, currentBrowser, dateTimeMail);
        Mail apiMail = new Mail(subject, text, (String) scenarioContext.getContextObj("senderMailLogin"),
                (String) scenarioContext.getContextObj("recipientMailLogin"));
        scenarioContext.setContext("apiMail", apiMail);
    }

    @And("^sending letter to recipient$")
    public void sendingLetterToRecipient() {
        Mail apiMail = (Mail) scenarioContext.getContextObj("apiMail");
        ((MailUtils) scenarioContext.getContextObj("sender")).messageGeneration(apiMail.getText(),
                apiMail.getSubject(), apiMail.getToAddress()).sendMail();
    }

    @And("^sending letter in send folder$")
    public void sendingLetterInSendFolder() {
        ((MailUtils) scenarioContext.getContextObj("sender")).addMsgInSentFolder();
    }
}