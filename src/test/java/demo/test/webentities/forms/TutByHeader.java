package demo.test.webentities.forms;

import demo.test.webentities.locators.ITutByHeader;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.aeonbits.owner.ConfigFactory;
import org.openqa.selenium.By;
import webdriver.BaseForm;
import webdriver.elements.Button;

import java.text.MessageFormat;

/**
 * The type Tut by header.
 */
public class TutByHeader extends BaseForm {

    private static ITutByHeader tutByHeaderLocators = ConfigFactory.create(ITutByHeader.class);
    private static final String MAIN_LOCATOR = tutByHeaderLocators.mainLocator();
    private final String locTopBarElement = tutByHeaderLocators.topBar();

    /**
     * Instantiates a new Tut by header.
     */
    public TutByHeader() {
        super(By.xpath(MAIN_LOCATOR), "tut.by Header Form");
    }

    /**
     * Click topbar element.
     *
     * @param element the element
     */
    public void clickTopBarElement(TopBar element) {
        new Button(By.xpath(MessageFormat.format(locTopBarElement, element.getTopBarLocator())),
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