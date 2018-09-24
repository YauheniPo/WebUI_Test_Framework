package demo.test.webentities.pages;

import demo.test.webentities.forms.TutByHeader;
import demo.test.webentities.locators.ITutByHomePage;
import lombok.Getter;
import org.aeonbits.owner.ConfigFactory;
import org.openqa.selenium.By;
import webdriver.BaseForm;

/**
 * The type Tut by home page.
 */
public class TutByHomePage extends BaseForm {

    private static final ITutByHomePage homePageLocators = ConfigFactory.create(ITutByHomePage.class);
    private static final String MAIN_LOCATOR = homePageLocators.mainLocator();

    @Getter private final TutByHeader header = new TutByHeader();

    /**
     * Instantiates a new Tut by home page.
     */
    public TutByHomePage() {
        super(By.xpath(MAIN_LOCATOR), "tut.by Home Page ");
    }
}