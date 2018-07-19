package demo.test.forms;

import org.openqa.selenium.By;
import webdriver.BaseForm;
import webdriver.elements.Button;

/**
 * The type User account dropdown.
 */
public class UserAccountDropdown extends BaseForm {
    private static final By MAIN_LOCATOR = By.xpath("//div[@role='dialog']");
    private static final String LOC_USER_DROPDOWN = "//div[@role='dialog']//*[contains(@href, '%s')]";

    /**
     * Instantiates a new User account dropdown.
     */
    public UserAccountDropdown() {
        super(MAIN_LOCATOR, "User Account Dropdown");
    }

    /**
     * Click user dropdown field.
     *
     * @param element the element
     */
    public void clickUserDropdownField(UserDropdown element) {
        new Button(By.xpath(String.format(LOC_USER_DROPDOWN, element.getLoc())), element.name()).click();
    }

    /**
     * The enum User dropdown.
     */
    public enum UserDropdown {
        LOGOUT("logout");

        private final String userLocator;

        /**
         * Instantiates a new User Dropdown.
         *
         * @param locator the locator
         */
        UserDropdown(String locator) {
            this.userLocator = locator;
        }

        /**
         * Gets locator.
         *
         * @return the locator
         */
        String getLoc() {
            return userLocator;
        }
    }
}