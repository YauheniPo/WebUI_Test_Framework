package demo.test.pages;

import demo.test.forms.MailsForm;
import demo.test.forms.UserAccountDropdown;
import org.openqa.selenium.By;
import webdriver.BaseForm;
import webdriver.elements.Button;

/**
 * The type Email account page.
 */
public class EmailAccountPage extends BaseForm {
    private static final By MAIN_LOCATOR = By.xpath("//*[@class='mail-App-Content']");
    private final By userAccountLocator = By.xpath("//div[contains(@class, 'Header')]//*[contains(@data-key, 'user')]");
    private static final String LOC_NAV_BAR_ELEMENT = "//div[contains(@data-key, 'left-box')]//a[contains(@href, '%s')]";
    private final MailsForm mailsForm = new MailsForm();

    /**
     * Instantiates a new Email account page.
     */
    public EmailAccountPage() {
        super(MAIN_LOCATOR, "Email Page");
    }

    /**
     * Gets mails form.
     *
     * @return the mails form
     */
    public MailsForm getMailsForm() {
        return mailsForm;
    }

    /**
     * Fetch email folder email account page.
     *
     * @param folder the folder
     *
     * @return the email account page
     */
    public EmailAccountPage fetchEmailFolder(NavBox folder) {
        new Button(By.xpath(String.format(LOC_NAV_BAR_ELEMENT, folder.getLoc())), String.format("Folder %s", folder.getLoc().toUpperCase())).click();
        return this;
    }

    /**
     * Click user account user account dropdown.
     *
     * @return the user account dropdown
     */
    public UserAccountDropdown clickUserAccount() {
        new Button(userAccountLocator, "User Account").click();
        return new UserAccountDropdown();
    }

    /**
     * The enum Navbox.
     */
    public enum NavBox {
        INBOX("inbox"),
        SENT("sent");

        private final String navBoxLocator;

        /**
         * Instantiates a new NavBox.
         *
         * @param locator the locator
         */
        NavBox(String locator) {
            this.navBoxLocator = locator;
        }

        /**
         * Gets locator.
         *
         * @return the locator
         */
        String getLoc() {
            return navBoxLocator;
        }
    }
}