package demo.test.forms;

import org.openqa.selenium.By;
import webdriver.BaseForm;
import webdriver.elements.Button;

public class TutByHeader extends BaseForm {
    private static final By MAIN_LOCATOR = By.xpath("//*[contains(@id, '_mainmenu')]");
    private final String loc_top_bar_element = "//*[contains(@id, '_mainmenu')]//ul[contains(@class, 'topbar-i')]//a[contains(@href, '%s')]";

    public TutByHeader() {
        super(MAIN_LOCATOR, "tut.by Header Form");
    }

    public void clickTopBarElement(TopBar element) {
        new Button(By.xpath(String.format(loc_top_bar_element, element.getLoc())), String.format("Button %s", element.getLoc().toUpperCase())).click();
    }

    public enum TopBar {
        EMAIL("mail");

        private final String topBarLocator;

        TopBar(String locator) {
            this.topBarLocator = locator;
        }

        public String getLoc() {
            return topBarLocator;
        }
    }
}