package demo.test.tests;

import demo.test.Models.Mail;
import demo.test.forms.AuthorizeEmailForm;
import demo.test.forms.TutByHeader;
import demo.test.forms.UserAccountDropdown;
import demo.test.pages.EmailAccountPage;
import demo.test.pages.TutByHomePage;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import webdriver.BaseTest;
import webdriver.Browser;
import webdriver.utils.mail.MailUtils;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * The type Tut by email test.
 */
public class TutByEmailTest extends BaseTest {
    private String senderMailLogin;
    private String recipientMailLogin;
	private String senderMailPassword;
	private String recipientMailPassword;
	private String emailText;
    private MailUtils mailSender;
	private String currentBrowser = Browser.getBrowserName();
	private ArrayList<MailUtils> mailBox = new ArrayList<>();

    /**
        * Init test data.
        *
        * @param emailText the email text
        */
    @BeforeTest
	@Parameters(value = {"emailText"})
	public void initTestData(String emailText) {
        this.emailText = emailText;
    }

    /**
        * Clear email and close mail store.
        */
    @AfterClass
	public void clearEmailAndCloseMailStore() {
		deleteMails();
        for(MailUtils mail : mailBox) {
            if (mail != null) {
                mail.closeStore();
            }
        }
	}

	@Override
	public void runTest(String senderMailLogin, String senderMailPassword,
                        String recipientMailLogin, String recipientMailPassword) {
        this.senderMailLogin = senderMailLogin;
        this.senderMailPassword = senderMailPassword;
        this.recipientMailLogin = recipientMailLogin;
        this.recipientMailPassword = recipientMailPassword;

		String dateTimeMail = new SimpleDateFormat("HH:mm").format(new Date());
		String subject = String.format("From %s", senderMailLogin);
		String text = String.format("%s_%s_%s", emailText, currentBrowser, dateTimeMail);

        // init Email sessions
        this.mailSender = getMailStore(senderMailLogin, senderMailPassword);
        getMailStore(recipientMailLogin, recipientMailPassword);
        // cleaning mail data
        deleteMails();

		logger.step(1, "Sending a message using api");
		Mail apiMail = new Mail(subject, text, senderMailLogin, recipientMailLogin);
		mailSender.sendMail(text, subject, recipientMailLogin);
		mailSender.addMsgInSentFolder();

		logger.step(2, "Opening the main page");
		TutByHomePage tutByHomePage = new TutByHomePage();

		logger.step(3, "Receiving data from the sender's mail");
		tutByHomePage.getHeader().clickTopBarElement(TutByHeader.TopBar.EMAIL);
		Mail senderMail = fetchAccountMail(EmailAccountPage.NavBox.SENT, senderMailLogin, senderMailPassword);
		logoutAccount();

		logger.step(4, "Opening the main page");
		Browser.openStartPage();
		tutByHomePage = new TutByHomePage();

		logger.step(5, "Receiving data from the sender's mail");
		tutByHomePage.getHeader().clickTopBarElement(TutByHeader.TopBar.EMAIL);
		Mail recipientMail = fetchAccountMail(EmailAccountPage.NavBox.INBOX, recipientMailLogin, recipientMailPassword);
		logoutAccount();

		logger.step(6, "Verification of the correctness of the data of the sent mail");
		equalsMails(apiMail, new LinkedList<>(Arrays.asList(senderMail, recipientMail)));
	}

	private Mail fetchAccountMail(EmailAccountPage.NavBox folder, String emailLogin, String emailPassword) {
		return new AuthorizeEmailForm().signIn(emailLogin, emailPassword).
				fetchEmailFolder(folder).getMailsForm().choiceLastMail().getMail();
	}

	private void logoutAccount() {
		new EmailAccountPage().clickUserAccount().clickUserDropdownField(UserAccountDropdown.UserDropdown.LOGOUT);
	}

    private MailUtils getMailStore(String login, String password) {
	    MailUtils mail = new MailUtils(login, password);
	    mailBox.add(mail);
	    return mail;
    }

    private void deleteMails() {
		for(MailUtils mail : mailBox) {
			if (mail != null) {
				mail.deleteAllMessages();
			}
		}
	}

	private void equalsMails(Mail apiMail, List<Mail> mails) {
		for (Mail mail : mails) {
			assertEquals(apiMail, mail);
            info("Expected Mail: '" + apiMail.toString() + "' same as Mail: '" + mail.toString() + "'");
		}
	}

    public String getReportData() {
        return String.format("%s, %s", this.senderMailLogin, this.recipientMailLogin);
    }
}