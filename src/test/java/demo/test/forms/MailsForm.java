package demo.test.forms;

import demo.test.models.Mail;
import org.openqa.selenium.By;
import webdriver.BaseForm;
import webdriver.elements.Label;

/**
 * The type Mails form.
 */
public class MailsForm extends BaseForm {
    private static final By MAIN_LOCATOR = By.xpath("//*[contains(@class, 'mail-Layout-Main')]");
    private final By locLastMail = By.xpath("//*[contains(@class, 'mail-Layout-Main')]//*[contains(@class, 'MessagesList')]/.");
    private final By locSender = By.xpath("//span[contains(@class, 'sender')]");
    private final By locRecipient = By.xpath("//div[contains(@class, 'user-name')]");
    private final By locSubject = By.xpath("//div[contains(@class, 'Subject')]");
    private final By locText = By.xpath("//div[contains(@class, 'body')]");

    /**
     * Instantiates a new Mails form.
     */
    public MailsForm() {
        super(MAIN_LOCATOR, "Mails Form");
    }

    /**
     * Choice last mail mails form.
     *
     * @return the mails form
     */
    public MailsForm choiceLastMail() {
        new Label(locLastMail, "Last Mail").click();
        return this;
    }

    /**
     * Gets mail.
     *
     * @return the mail
     */
    public Mail getMail() {
        String subject = new Label(locSubject, "Subject field").getText();
        String text = new Label(locText, "Mail text").getText();
        String fromAddress = new Label(locSender, "Sender name").getText();
        String toAddress = new Label(locRecipient, "Recipient name").getText();
        return new Mail(subject, text, fromAddress, toAddress);
    }
}