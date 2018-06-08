package demo.test.tests;

import demo.test.forms.AuthorizeEmailForm;
import demo.test.forms.TutByHeader;
import demo.test.forms.UserAccountDropdown;
import demo.test.pages.EmailAccountPage;
import demo.test.pages.TutByHomePage;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import webdriver.BaseTest;
import webdriver.Browser;
import webdriver.utils.mail.MailUtils;

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

		//abstractFactory
	}

	@AfterClass
	public void closeMailStore() {
        for(MailUtils mail : mailBox) {
            if (mail != null) {
                mail.closeStore();
            }
        }
	}

	@AfterMethod
    public void deleteEmailFolders() {
	    for(MailUtils mail : mailBox) {
		    if (mail != null) {
                logger.info("Очищаем входящую почту");
                mail.deleteAllMessages();
            }
		}
    }

	@Override
	public void runTest() {

		logger.step(1, "mail");

		mailSender = getMailStore(senderMailLogin, senderMailPassword);
		Date dateTimeMail = new Date();
		mailSender.sendMail(String.format("%s_%s_%s", emailText, currentBrowser, dateTimeMail), String.format("From %s", senderMailLogin), recipientMailLogin);
		mailSender.addMsgInSentFolder();
		mailRecipient = getMailStore(recipientMailLogin, recipientMailPassword);

		TutByHomePage tutByHomePage = new TutByHomePage();
		tutByHomePage.getHeader().clickTopBarElement(TutByHeader.TopBar.EMAIL);

		new AuthorizeEmailForm().signIn(senderMailLogin, senderMailPassword).fetchEmailFolder(EmailAccountPage.NavBox.SENT).getMailsForm();

		new EmailAccountPage().clickUserAccount().clickUserDropdownField(UserAccountDropdown.UserDropdown.LOGOUT);
		Browser.openStartPage();
	}

    private MailUtils getMailStore(String login, String password) {
	    MailUtils mail = new MailUtils(login, password);
	    mailBox.add(mail);
	    return mail;
    }
}