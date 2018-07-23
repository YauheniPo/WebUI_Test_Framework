package demo.test.forms;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.openqa.selenium.By;
import webdriver.BaseForm;
import webdriver.elements.Button;

/**
 * The type Tut by header.
 */
public class TutByHeader extends BaseForm {
    private static final By MAIN_LOCATOR = By.xpath("//*[contains(@id, '_mainmenu')]");
    private static final String LOC_TOP_BAR_ELEMENT = "//*[contains(@id, '_mainmenu')]//ul[contains(@class, 'topbar-i')]//a[contains(@href, '%s')]";

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
        new Button(By.xpath(String.format(LOC_TOP_BAR_ELEMENT, element.getTopBarLocator())),
                   String.format("Button %s", element.getTopBarLocator().toUpperCase())).click();
    }

    /**
     * The enum Topbar.
     */
    @Getter
    @AllArgsConstructor(access = AccessLevel.MODULE)
    public enum TopBar {
        EMAIL("mail");

        private final String topBarLocator;
    }
}