package demo.test.pages;

import demo.test.forms.MailsForm;
import demo.test.forms.UserAccountDropdown;
import org.openqa.selenium.By;
import webdriver.BaseForm;
import webdriver.elements.Button;

public class EmailAccountPage extends BaseForm {
    private static final By MAIN_LOCATOR = By.xpath("//*[@class='header-logo']");
    private final By userAccountLocator = By.xpath("//div[contains(@class, 'Header')]//*[contains(@data-key, 'user')]");
    private final String locNavBarElement = "//div[contains(@data-key, 'left-box')]//a[contains(@href, '%s')]";
    private MailsForm mailsForm = new MailsForm();

    public EmailAccountPage() {
        super(MAIN_LOCATOR, "Email Page");
    }

    public MailsForm getMailsForm() {
        return mailsForm;
    }

    public EmailAccountPage fetchEmailFolder(NavBox folder) {
        new Button(By.xpath(String.format(locNavBarElement, folder.getLoc())), String.format("Folder %s", folder.getLoc().toUpperCase())).click();
        return this;
    }

    public UserAccountDropdown clickUserAccount() {
        new Button(userAccountLocator, "User Account").click();
        return new UserAccountDropdown();
    }

    public enum NavBox {
        INBOX("inbox"), SENT("sent");

        private final String navBoxLocator;

        NavBox(String locator) {
            this.navBoxLocator = locator;
        }

        public String getLoc() {
            return navBoxLocator;
        }
    }
}