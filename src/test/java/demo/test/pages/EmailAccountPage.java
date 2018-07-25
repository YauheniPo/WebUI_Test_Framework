package demo.test.pages;

import demo.test.forms.AccountForm;
import demo.test.forms.MailsForm;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.openqa.selenium.By;
import webdriver.BaseForm;
import webdriver.elements.Button;

/**
 * The type Email account page.
 */
public class EmailAccountPage extends BaseForm {
    private static final By MAIN_LOCATOR = By.xpath("//*[@class='mail-App-Content']");
    private final String locNavBarElement = "//div[contains(@data-key, 'left-box')]//a[contains(@href, '%s')]";
    @Getter private final MailsForm mailsForm = new MailsForm();
    @Getter private final AccountForm accountForm = new AccountForm();

    /**
     * Instantiates a new Email account page.
     */
    public EmailAccountPage() {
        super(MAIN_LOCATOR, "Email Page");
    }

    /**
     * Fetch email folder email account page.
     *
     * @param folder the folder
     *
     * @return the email account page
     */
    public EmailAccountPage fetchEmailFolder(NavBox folder) {
        new Button(By.xpath(String.format(locNavBarElement, folder.getNavBoxLocator())),
                String.format("Folder %s", folder.getNavBoxLocator().toUpperCase())).click();
        return this;
    }

    /**
     * The enum Navbox.
     */
    @Getter
    @AllArgsConstructor(access = AccessLevel.MODULE)
    public enum NavBox {
        INBOX("inbox"),
        SENT("sent");

        private final String navBoxLocator;
    }
}