package steps;

import cucumber.api.java.After;
import cucumber.api.java.en.Given;
import demo.test.models.TestDataMails;
import demo.test.utils.FactoryInitParams;
import webdriver.Browser;
import webdriver.Logger;
import webdriver.utils.mail.MailUtils;

import java.util.ArrayList;

public class ApiEmailSteps extends BaseSteps {
    private String senderMailLogin;
    private String recipientMailLogin;
    private String senderMailPassword;
    private String recipientMailPassword;
    private MailUtils mailSender;
    private final String currentBrowser = Browser.getBrowserName();
    private final ArrayList<MailUtils> mailBox = new ArrayList<>();

    @Given("^test data$")
    public void getTestData() {
        TestDataMails testDataMails = (TestDataMails) (getParams()[0]);
        this.senderMailLogin = testDataMails.getSenderMailLogin();
        this.senderMailPassword = testDataMails.getSenderMailPassword();
        this.recipientMailLogin = testDataMails.getRecipientMailLogin();
        this.recipientMailPassword = testDataMails.getRecipientMailPassword();
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

    /**
     * Delete mails.
     */
    private void deleteMails() {
        for (MailUtils mail : mailBox) {
            if (mail != null) {
                mail.deleteAllMessages();
            }
        }
    }

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
}
