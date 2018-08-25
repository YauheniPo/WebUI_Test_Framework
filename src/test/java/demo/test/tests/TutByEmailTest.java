package demo.test.tests;

import demo.test.forms.AccountForm;
import demo.test.forms.TutByHeader;
import demo.test.pages.AuthorizeEmailPageFactory;
import demo.test.models.Mail;
import demo.test.models.TestDataMails;
import demo.test.pages.EmailAccountPage;
import demo.test.pages.TutByHomePage;
import demo.test.utils.FactoryInitParams;
import io.qameta.allure.Step;
import org.springframework.beans.factory.annotation.Value;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import webdriver.BaseTestDataProvider;
import webdriver.driver.Browser;
import webdriver.utils.mail.MailUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

/**
 * The type Tut by email test.
 */
public class TutByEmailTest extends BaseTestDataProvider {
    private String senderMailLogin;
    private String recipientMailLogin;
    private String senderMailPassword;
    private String recipientMailPassword;
    private MailUtils mailSender;
    private final String currentBrowser = Browser.getBrowserName();
    private final ArrayList<MailUtils> mailBox = new ArrayList<>();
    private Predicate<Object> elIsNull = Objects::nonNull;
    @Value("${emailText}")
    private String emailText = "epam_e.popovich";

    /**
     * Clear email and close mail store.
     */
    @AfterClass
    public void clearEmailAndCloseMailStore() {
        deleteMails();
        mailBox.forEach(mail -> {if(elIsNull.test(mail)) {mail.closeStore();}});
    }

    /**
     * Return base page.
     */
    @AfterMethod
    public void returnBasePage() {
        Browser.openStartPage();
    }

    /**
     * Run test.
     *
     * @param testData the test data
     */
    @Override
    public void runTest(Object testData) {
        TestDataMails testDataMails = (TestDataMails) testData;
        this.senderMailLogin = testDataMails.getSenderMailLogin();
        this.senderMailPassword = testDataMails.getSenderMailPassword();
        this.recipientMailLogin = testDataMails.getRecipientMailLogin();
        this.recipientMailPassword = testDataMails.getRecipientMailPassword();

        String dateTimeMail = new SimpleDateFormat("HH:mm").format(new Date());
        String subject = String.format("From %s", this.senderMailLogin);
        String text = String.format("%s_%s_%s", emailText, currentBrowser, dateTimeMail);

        // init Email sessions
        this.mailSender = getMailStore(this.senderMailLogin, this.senderMailPassword);
        getMailStore(this.recipientMailLogin, this.recipientMailPassword);
        // cleaning mail data
        deleteMails();

        LOGGER.step(1, "Sending a message using api");
        Mail apiMail = new Mail(subject, text, this.senderMailLogin, this.recipientMailLogin);
        mailSender.messageGeneration(apiMail.getText(), apiMail.getSubject(), apiMail.getToAddress()).sendMail();
        mailSender.addMsgInSentFolder();

        LOGGER.step(2, "Opening the main page");
        TutByHomePage tutByHomePage = new TutByHomePage();

        LOGGER.step(3, "Receiving data from the sender's mail");
        tutByHomePage.getHeader().clickTopBarElement(TutByHeader.TopBar.EMAIL);
        Mail senderMail = fetchAccountMail(EmailAccountPage.NavBox.SENT, this.senderMailLogin, this.senderMailPassword);
        logoutAccount();
        checkAuthorization(this.senderMailLogin, false);

        LOGGER.step(4, "Opening the main page");
        Browser.openStartPage();
        tutByHomePage = new TutByHomePage();

        LOGGER.step(5, "Receiving data from the sender's mail");
        tutByHomePage.getHeader().clickTopBarElement(TutByHeader.TopBar.EMAIL);
        Mail recipientMail = fetchAccountMail(EmailAccountPage.NavBox.INBOX, this.recipientMailLogin, this.recipientMailPassword);
        logoutAccount();
        checkAuthorization(this.recipientMailLogin, false);

        LOGGER.step(6, "Verification of the correctness of the data of the sent mail");
        equalsMails(apiMail, new LinkedList<>(Arrays.asList(senderMail, recipientMail)));
    }

    /**
     * Fetch account mail mail.
     *
     * @param folder        the folder
     * @param emailLogin    the email login
     * @param emailPassword the email password
     *
     * @return the mail
     */
    @Step("{0} {1} {2}")
    private Mail fetchAccountMail(EmailAccountPage.NavBox folder, String emailLogin, String emailPassword) {
//        EmailAccountPage emailAccountPage = new AuthorizeEmailPage().signIn(emailLogin, emailPassword);
        EmailAccountPage emailAccountPage = new AuthorizeEmailPageFactory().signIn(emailLogin, emailPassword);
        checkAuthorization(emailLogin, true);
        return emailAccountPage.fetchEmailFolder(folder).getMailsForm().choiceLastMail().getMail();
    }

    /**
     * Check Authorization
     *
     * @param login          login
     * @param expectedResult expected result
     */
    @Step("{0} {1}")
    private void checkAuthorization(String login, Boolean expectedResult) {
        LOGGER.info("Verify authorization");
        ASSERT_WRAPPER.assertEquals(new AccountForm().isAuthorized(login), expectedResult);
    }

    /**
     * Logout account.
     */
    private void logoutAccount() {
        new EmailAccountPage().getAccountForm().clickUserAccount().clickUserDropdownField(AccountForm.UserDropdown.LOGOUT);
    }

    /**
     * Gets mail store.
     *
     * @param login    the login
     * @param password the password
     *
     * @return the mail store
     */
    @Step("{0} {1}")
    private MailUtils getMailStore(String login, String password) {
        MailUtils mail = new MailUtils(login, password);
        mailBox.add(mail);
        return mail;
    }

    /**
     * Delete mails.
     */
    private void deleteMails() {
        mailBox.forEach(mail -> {if(elIsNull.test(mail)) {mail.deleteAllMessages();}});
    }

    /**
     * Equals mails.
     *
     * @param apiMail the api mail
     * @param mails   the mails
     */
    private void equalsMails(Mail apiMail, List<Mail> mails) {
        mails.forEach(mail -> {
            ASSERT_WRAPPER.assertEquals(apiMail, mail);
            LOGGER.info("Expected Mail: '" + apiMail.toString() + "' same as Mail: '" + mail.toString() + "'");
        });
    }

    /**
     * Gets report data.
     *
     * @return the report data
     */
    @Override
    public String getReportData() {
        return String.format("%s, %s", this.senderMailLogin, this.recipientMailLogin);
    }

    @Override
    protected Object[] getTestData(String dataBaseLocation) {
        return new FactoryInitParams().getTestData(dataBaseLocation);
    }
}