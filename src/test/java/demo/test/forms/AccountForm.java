package demo.test.forms;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.openqa.selenium.By;
import webdriver.BaseForm;
import webdriver.elements.Button;
import webdriver.elements.Label;

/**
 * The type User account dropdown.
 */
public class AccountForm extends BaseForm {
    private static final By MAIN_LOCATOR = By.xpath("//div[contains(@class, 'head-user') or contains(@class, 'login-title')]");
    private final String locUserDropdown = "//div[contains(@class, 'dropdown-content')]//*[contains(@href, '%s')]";

    /**
     * Instantiates a new User account dropdown.
     */
    public AccountForm() {
        super(MAIN_LOCATOR, "User Account Dropdown");
    }

    /**
     * Click user account user account dropdown.
     *
     * @return the user account dropdown
     */
    public AccountForm clickUserAccount() {
        new Button(MAIN_LOCATOR, "User Account").click();
        return new AccountForm();
    }

    /**
     * Gets user name.
     *
     * @return the user name
     */
    public boolean isAuthorized(String login) {
        Label userNameLabel = new Label(MAIN_LOCATOR, "User Name");
        return userNameLabel.isPresent() && userNameLabel.getText().contains(login);
    }

    /**
     * Click user dropdown field.
     *
     * @param element the element
     */
    public void clickUserDropdownField(UserDropdown element) {
        new Button(By.xpath(String.format(locUserDropdown, element.getUserLocator())), element.name()).click();
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