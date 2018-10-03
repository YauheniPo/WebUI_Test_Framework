package demo.test.webentities.forms;

import demo.test.webentities.locators.IMailsForm;
import demo.test.webentities.models.Mail;
import org.aeonbits.owner.ConfigFactory;
import org.openqa.selenium.By;
import webdriver.BaseForm;
import webdriver.elements.Label;

/**
 * The type Mails form.
 */
public class MailsForm extends BaseForm {

    private static final IMailsForm mailsFormLocators = ConfigFactory.create(IMailsForm.class);
    private static final String MAIN_LOCATOR = mailsFormLocators.mainLocator();
    private final String locLastMail = mailsFormLocators.lastMail();
    private final String locSender = mailsFormLocators.sender();
    private final String locRecipient = mailsFormLocators.recipient();
    private final String locSubject = mailsFormLocators.subject();
    private final String locText = mailsFormLocators.text();

    /**
     * Instantiates a new Mails form.
     */
    public MailsForm() {
        super(By.xpath(MAIN_LOCATOR), "Mails Form");
    }

    /**
     * Choice last mail mails form.
     *
     * @return the mails form
     */
    public MailsForm choiceLastMail() {
        new Label(By.xpath(this.locLastMail), "Last Mail").click();
        return this;
    }

    /**
     * Gets mail.
     *
     * @return the mail
     */
    public Mail getMail() {
        String subject = new Label(By.xpath(this.locSubject), "Subject field").getText();
        String text = new Label(By.xpath(this.locText), "Mail text").getText();
        String fromAddress = new Label(By.xpath(this.locSender), "Sender name").getText();
        String toAddress = new Label(By.xpath(this.locRecipient), "Recipient name").getText();
        return new Mail(subject, text, fromAddress, toAddress);
    }
}