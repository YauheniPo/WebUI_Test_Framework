package demo.test.forms;

import org.openqa.selenium.By;
import webdriver.BaseForm;
import webdriver.elements.Button;

/**
 * The type User account dropdown.
 */
public class UserAccountDropdown extends BaseForm {
    private static final By MAIN_LOCATOR = By.xpath("//div[@role='dialog']");
    private final String locUserDropdown = "//div[@role='dialog']//*[contains(@href, '%s')]";

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
        new Button(By.xpath(String.format(locUserDropdown, element.getLoc())), element.name()).click();
    }

    /**
     * The enum User dropdown.
     */
    public enum UserDropdown {
        /**
         * Logout user dropdown.
         */
        LOGOUT("logout");

        private final String userLocator;

        UserDropdown(String locator) {
            this.userLocator = locator;
        }

        /**
         * Gets locator.
         *
         * @return the locator
         */
        public String getLoc() {
            return userLocator;
        }
    }
}