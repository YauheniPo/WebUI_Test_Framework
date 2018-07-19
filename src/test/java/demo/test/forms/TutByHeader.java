package demo.test.forms;

import org.openqa.selenium.By;
import webdriver.BaseForm;
import webdriver.elements.Button;

/**
 * The type Tut by header.
 */
public class TutByHeader extends BaseForm {
    private static final By MAIN_LOCATOR = By.xpath("//*[contains(@id, '_mainmenu')]");
    private static final String
        LOC_TOP_BAR_ELEMENT = "//*[contains(@id, '_mainmenu')]//ul[contains(@class, 'topbar-i')]//a[contains(@href, '%s')]";

    /**
     * Instantiates a new Tut by header.
     */
    public TutByHeader() {
        super(MAIN_LOCATOR, "tut.by Header Form");
    }

    /**
     * Click topbar element.
     *
     * @param element the element
     */
    public void clickTopBarElement(TopBar element) {
        new Button(By.xpath(String.format(LOC_TOP_BAR_ELEMENT, element.getLoc())),
                   String.format("Button %s", element.getLoc().toUpperCase())).click();
    }

    /**
     * The enum Topbar.
     */
    public enum TopBar {
        EMAIL("mail");

        private final String topBarLocator;

        /**
         * Instantiates a new TopBar.
         *
         * @param locator the locator
         */
        TopBar(String locator) {
            this.topBarLocator = locator;
        }

        /**
         * Gets locator.
         *
         * @return the locator
         */
        String getLoc() {
            return topBarLocator;
        }
    }
}