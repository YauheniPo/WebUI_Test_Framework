package demo.test.webentities.pages;

import demo.test.webentities.forms.AccountForm;
import demo.test.webentities.forms.MailsForm;
import demo.test.webentities.locators.IEmailAccountPage;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.aeonbits.owner.ConfigFactory;
import org.openqa.selenium.By;
import webdriver.BaseForm;
import webdriver.elements.Button;

import java.text.MessageFormat;

/**
 * The type Email account page.
 */
public class EmailAccountPage extends BaseForm {

    private static IEmailAccountPage emailAccountPageLocators = ConfigFactory.create(IEmailAccountPage.class);
    private static final String MAIN_LOCATOR = emailAccountPageLocators.mainLocator();
    private final String navBarLocator = emailAccountPageLocators.navBar();

    @Getter private final MailsForm mailsForm = new MailsForm();
    @Getter private final AccountForm accountForm = new AccountForm();

    /**
     * Instantiates a new Email account page.
     */
    public EmailAccountPage() {
        super(By.xpath(MAIN_LOCATOR), "Email Page");
    }

    /**
     * Fetch email folder email account page.
     *
     * @param folder the folder
     *
     * @return the email account page
     */
    public EmailAccountPage fetchEmailFolder(NavBox folder) {
        new Button(By.xpath(MessageFormat.format(navBarLocator, folder.getNavBoxLocator())),
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