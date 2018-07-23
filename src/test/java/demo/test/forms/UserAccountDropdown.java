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
public class UserAccountDropdown extends BaseForm {
    private static final By MAIN_LOCATOR = By.xpath("//div[@role='dialog']");
    private static final String LOC_USER_DROPDOWN = "//div[@role='dialog']//*[contains(@href, '%s')]";
    private static final By LOC_USER_NAME = By.xpath("//div[contains(@class, 'user-name') or contains(@class, 'User-Name')]");

    /**
     * Instantiates a new User account dropdown.
     */
    public UserAccountDropdown() {
        super(MAIN_LOCATOR, "User Account Dropdown");
    }

    /**
     * Gets user name.
     *
     * @return the user name
     */
    public String getUserName() {
        Label userNameLabel = new Label(LOC_USER_NAME, "User Name");
        return userNameLabel.isPresent() ? userNameLabel.getText() : null;
    }

    /**
     * Click user dropdown field.
     *
     * @param element the element
     */
    public void clickUserDropdownField(UserDropdown element) {
        new Button(By.xpath(String.format(LOC_USER_DROPDOWN, element.getUserLocator())), element.name()).click();
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