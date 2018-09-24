package demo.test.webentities.forms;

import demo.test.webentities.locators.IAccountForm;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.aeonbits.owner.ConfigFactory;
import org.openqa.selenium.By;
import webdriver.BaseForm;
import webdriver.elements.Button;
import webdriver.elements.Label;

import java.text.MessageFormat;

/**
 * The type User account dropdown.
 */
public class AccountForm extends BaseForm {

    private static final IAccountForm accountFormLocators = ConfigFactory.create(IAccountForm.class);
    private static final String MAIN_LOCATOR = accountFormLocators.mainLocator();
    private final String locUserDropdown = accountFormLocators.userDropdown();

    /**
     * Instantiates a new User account dropdown.
     */
    public AccountForm() {
        super(By.xpath(MAIN_LOCATOR), "User Account Dropdown");
    }

    /**
     * Click user account user account dropdown.
     *
     * @return the user account dropdown
     */
    public AccountForm clickUserAccount() {
        new Button(By.xpath(MAIN_LOCATOR), "User Account").click();
        return new AccountForm();
    }

    /**
     * Gets user name.
     *
     * @return the user name
     */
    public boolean isAuthorized(String login) {
        Label userNameLabel = new Label(By.xpath(MAIN_LOCATOR), "User Name");
        return userNameLabel.isPresent() && userNameLabel.getText().contains(login);
    }

    /**
     * Click user dropdown field.
     *
     * @param element the element
     */
    public void clickUserDropdownField(UserDropdown element) {
        new Button(By.xpath(MessageFormat.format(locUserDropdown, element.getUserLocator())), element.name()).click();
    }

    /**
     * The enum User dropdown.
     */
    @Getter
    @AllArgsConstructor(access = AccessLevel.MODULE)
    public enum UserDropdown {
        LOGOUT("logout");

        private final String userLocator;
    }
}