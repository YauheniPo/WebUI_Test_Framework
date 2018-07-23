package demo.test.pages;

import demo.test.forms.TutByHeader;
import lombok.Getter;
import org.openqa.selenium.By;
import webdriver.BaseForm;

/**
 * The type Tut by home page.
 */
public class TutByHomePage extends BaseForm {
    private static final By MAIN_LOCATOR = By.xpath("//*[@class='header-logo']");
    @Getter private final TutByHeader header = new TutByHeader();

    /**
     * Instantiates a new Tut by home page.
     */
    public TutByHomePage() {
        super(MAIN_LOCATOR, "tut.by Home Page ");
    }
}