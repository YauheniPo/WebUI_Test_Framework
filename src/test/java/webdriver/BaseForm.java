package webdriver;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import webdriver.driver.Browser;

/**
 * The type Base form.
 */
public abstract class BaseForm extends BaseEntity {

    /**
     * Instantiates a new Base form.
     *
     * @param locator   the locator
     * @param formTitle the form title
     */
    protected BaseForm(final By locator, final String formTitle) {
        super(locator, formTitle);
//        PageFactory.initElements(Browser.getDriver(), this);
        checkPageMainElement();
    }

    /**
     * Check page main element.
     */
    private void checkPageMainElement() {
        LOGGER.info(String.format("Initialization %s element locator", this.title));
        if (!Browser.getDriver().findElement(this.titleLocator).isDisplayed()) {
            LOGGER.error(String.format("Page %s didn't initialization", this.title));
            throw new NoSuchElementException(this.title);
        }
    }
}