package demo.test.forms;

import org.openqa.selenium.By;
import webdriver.BaseForm;
import webdriver.elements.Button;

public class UserAccountDropdown extends BaseForm {
    private static final By MAIN_LOCATOR = By.xpath("//div[@role='dialog']");
    private final String locUserDropdown = "//div[@role='dialog']//*[contains(@href, '%s')]";

    public UserAccountDropdown() {
        super(MAIN_LOCATOR, "User Account Dropdown");
    }

    public void clickUserDropdownField(UserDropdown element) {
        new Button(By.xpath(String.format(locUserDropdown, element.getLoc())), element.name()).click();
    }

    public enum UserDropdown {
        LOGOUT("logout");

        private final String userLocator;

        UserDropdown(String locator) {
            this.userLocator = locator;
        }

        public String getLoc() {
            return userLocator;
        }
    }
}