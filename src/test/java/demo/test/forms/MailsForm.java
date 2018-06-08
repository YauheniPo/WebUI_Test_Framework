package demo.test.forms;

import org.openqa.selenium.By;
import webdriver.BaseForm;

public class MailsForm extends BaseForm {
    private static final By MAIN_LOCATOR = By.xpath("//*[contains(@class, 'mail-Layout-Main')]");
    public MailsForm() {
        super(MAIN_LOCATOR, "Mails Form");
    }
}