package demo.test.tests;

import demo.test.Models.Mail;
import demo.test.forms.AuthorizeEmailForm;
import demo.test.forms.TutByHeader;
import demo.test.forms.UserAccountDropdown;
import demo.test.pages.EmailAccountPage;
import demo.test.pages.TutByHomePage;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import webdriver.BaseTest;
import webdriver.Browser;
import webdriver.utils.mail.MailUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class TutByEmailTest extends BaseTest {
	private String emailText;
	private String senderMailLogin;
	private String senderMailPassword;
	private String recipientMailLogin;
	private String recipientMailPassword;
	private MailUtils mailSender;
	private MailUtils mailRecipient;
	private String currentBrowser = Browser.getBrowserName();
	private ArrayList<MailUtils> mailBox = new ArrayList<>();

	@BeforeClass
	@Parameters(value = { "email_text", "sender_mail_login", "sender_mail_password",
                          "recipient_mail_login", "recipient_mail_password" })
	public void initTestData(String email_text, String sender_mail_login, String sender_mail_password,
                             String recipient_mail_login, String recipient_mail_password){
		this.emailText = email_text;
		this.senderMailLogin = sender_mail_login;
		this.senderMailPassword = sender_mail_password;
		this.recipientMailLogin = recipient_mail_login;
		this.recipientMailPassword = recipient_mail_password;

		mailSender = getMailStore(senderMailLogin, senderMailPassword);
		mailRecipient = getMailStore(recipientMailLogin, recipientMailPassword);
		deleteMails();

		//abstractFactory
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
	public void runTest() {

		logger.step(1, "@tut.by test");

		String dateTimeMail = new SimpleDateFormat("HH:mm").format(new Date());
		String subject = String.format("From %s", senderMailLogin);
		String text = String.format("%s_%s_%s", emailText, currentBrowser, dateTimeMail);
		Mail apiMail = new Mail(subject, text, senderMailLogin, recipientMailLogin);

		mailSender.sendMail(text, subject, recipientMailLogin);
		mailSender.addMsgInSentFolder();

		TutByHomePage tutByHomePage = new TutByHomePage();
		tutByHomePage.getHeader().clickTopBarElement(TutByHeader.TopBar.EMAIL);

		Mail senderMail = new AuthorizeEmailForm().signIn(senderMailLogin, senderMailPassword).
				fetchEmailFolder(EmailAccountPage.NavBox.SENT).getMailsForm().choiceLastMail().getMail();

		new EmailAccountPage().clickUserAccount().clickUserDropdownField(UserAccountDropdown.UserDropdown.LOGOUT);

		Browser.openStartPage();

		tutByHomePage = new TutByHomePage();
		tutByHomePage.getHeader().clickTopBarElement(TutByHeader.TopBar.EMAIL);

		Mail recipientMail = new AuthorizeEmailForm().signIn(recipientMailLogin, recipientMailPassword).
				fetchEmailFolder(EmailAccountPage.NavBox.INBOX).getMailsForm().choiceLastMail().getMail();

		new EmailAccountPage().clickUserAccount().clickUserDropdownField(UserAccountDropdown.UserDropdown.LOGOUT);

		assertEquals(apiMail, senderMail);
		assertEquals(apiMail, recipientMail);
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
}