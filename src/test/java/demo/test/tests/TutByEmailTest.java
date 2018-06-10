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

public class TutByEmailTest extends BaseTest {
    private String senderMailLogin;
    private String recipientMailLogin;
	private String senderMailPassword;
	private String recipientMailPassword;
	private String emailText;
    private MailUtils mailSender;
	private String currentBrowser = Browser.getBrowserName();
	private ArrayList<MailUtils> mailBox = new ArrayList<>();

	@BeforeTest
	@Parameters(value = {"emailText"})
	public void initTestData(String emailText) {
        this.emailText = emailText;
    }

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
		Mail senderMail = new AuthorizeEmailForm().signIn(senderMailLogin, senderMailPassword).
				fetchEmailFolder(EmailAccountPage.NavBox.SENT).getMailsForm().choiceLastMail().getMail();
		new EmailAccountPage().clickUserAccount().clickUserDropdownField(UserAccountDropdown.UserDropdown.LOGOUT);

		logger.step(4, "Opening the main page");
		Browser.openStartPage();
		tutByHomePage = new TutByHomePage();

		logger.step(5, "Receiving data from the sender's mail");
		tutByHomePage.getHeader().clickTopBarElement(TutByHeader.TopBar.EMAIL);
		Mail recipientMail = new AuthorizeEmailForm().signIn(recipientMailLogin, recipientMailPassword).
				fetchEmailFolder(EmailAccountPage.NavBox.INBOX).getMailsForm().choiceLastMail().getMail();
		new EmailAccountPage().clickUserAccount().clickUserDropdownField(UserAccountDropdown.UserDropdown.LOGOUT);

		logger.step(6, "Verification of the correctness of the data of the sent mail");
		equalsMails(new LinkedList<>(Arrays.asList(senderMail, recipientMail, apiMail)));
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

	private void equalsMails(List<Mail> mails) {
		Mail mailFirst = mails.remove(0);
		for (Mail mail : mails) {
			assertEquals(mailFirst, mail);
            info("Expected Mail: '" + mailFirst.toString() + "' same as Mail: '" + mail.toString() + "'");
		}
	}

    public String getReportData() {
        return String.format("%s, %s", this.senderMailLogin, this.recipientMailLogin);
    }
}