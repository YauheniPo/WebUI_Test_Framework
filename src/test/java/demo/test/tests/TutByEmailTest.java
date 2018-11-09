package demo.test.tests;

import demo.test.webentities.forms.AccountForm;
import demo.test.webentities.forms.TutByHeader;
import demo.test.webentities.pages.AuthorizeEmailPage;
import demo.test.webentities.models.Mail;
import demo.test.webentities.models.TestDataMails;
import demo.test.webentities.pages.EmailAccountPage;
import demo.test.webentities.pages.TutByHomePage;
import io.qameta.allure.Step;
import lombok.NonNull;
import org.testng.annotations.*;
import webdriver.BaseDriverTest;
import webdriver.IDataProvider;
import webdriver.driver.Browser;
import webdriver.utils.mail.MailUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;
import java.util.function.Predicate;

/**
 * The type Tut by email test.
 */
public class TutByEmailTest extends BaseDriverTest implements IDataProvider {
    private String senderMailLogin;
    private String recipientMailLogin;
    private String senderMailPassword;
    private String recipientMailPassword;
    private MailUtils mailSender;
    private final String currentBrowser = Browser.getBrowserName();
    private final ArrayList<MailUtils> mailBox = new ArrayList<>();
    private final Predicate<Object> nonNull = Objects::nonNull;
    private String emailText;
    private String dataBaseLocation;

    @Parameters({"email_text", "data_base_location"})
    @BeforeClass
    public void fetchTestData(@Optional(value = "test_text") String emailText, @NonNull String dataBaseLocation) {
        this.emailText = emailText;
        this.dataBaseLocation = dataBaseLocation;
    }

    @AfterClass
    public void clearEmailAndCloseMailStore() {
        deleteMails();
        this.mailBox.forEach(mail -> {if(this.nonNull.test(mail)) {mail.closeStore();}});
    }

    @Test(dataProvider = "initParams")
    public void emailTest(@NonNull TestDataMails testDataEmails) {
        this.senderMailLogin = testDataEmails.getSenderMailLogin();
        this.senderMailPassword = testDataEmails.getSenderMailPassword();
        this.recipientMailLogin = testDataEmails.getRecipientMailLogin();
        this.recipientMailPassword = testDataEmails.getRecipientMailPassword();

        String dateTimeMail = new SimpleDateFormat("HH:mm").format(new Date());
        String subject = String.format("From %s", this.senderMailLogin);
        String text = String.format("%s_%s_%s", this.emailText, this.currentBrowser, dateTimeMail);

        // init Email sessions
        this.mailSender = getMailStore(this.senderMailLogin, this.senderMailPassword);
        getMailStore(this.recipientMailLogin, this.recipientMailPassword);
        // cleaning mail data
        deleteMails();

        LOGGER.step(1, "Sending a message using api");
        Mail apiMail = new Mail(subject, text, this.senderMailLogin, this.recipientMailLogin);
        this.mailSender.messageGeneration(apiMail.getText(), apiMail.getSubject(), apiMail.getToAddress()).sendMail();
        this.mailSender.addMsgInSentFolder();

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

        Browser.openStartPage();

        LOGGER.step(6, "Verification of the correctness of the data of the sent mail");
        equalsMails(apiMail, senderMail, recipientMail);
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
        EmailAccountPage emailAccountPage = new AuthorizeEmailPage().signIn(emailLogin, emailPassword);
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
        this.mailBox.add(mail);
        return mail;
    }

    /**
     * Delete mails.
     */
    private void deleteMails() {
        this.mailBox.forEach(mail -> {if(this.nonNull.test(mail)) {mail.deleteAllMessages();}});
    }

    /**
     * Equals mails.
     *
     * @param apiMail the api mail
     * @param mails   the mails
     */
    private void equalsMails(Mail apiMail, Mail...mails) {
        Arrays.asList(mails).forEach(mail -> ASSERT_WRAPPER.assertEquals(apiMail, mail));
    }

    @Override
    public String getDataBaseLocation() {
        return this.dataBaseLocation;
    }
}