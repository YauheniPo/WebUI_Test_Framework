package webdriver;

import org.openqa.selenium.By;
import webdriver.elements.Label;

/**
 * The type Base form.
 */
public abstract class BaseForm extends BaseEntity {
    private By titleLocator;
    private String title;

    /**
     * Instantiates a new Base form.
     *
     * @param locator   the locator
     * @param formTitle the form title
     */
    protected BaseForm(final By locator, final String formTitle) {
        this.titleLocator = locator;
        this.title = formTitle;
        info(String.format("Locator of '%1$s' Form", this.title));
        checkFormIsDisplayed();
    }

    /**
     * Check form is displayed.
     */
    private void checkFormIsDisplayed() {
        try {
            new Label(titleLocator, title).waitForIsElementPresent();
        } catch (Exception e) {
            debug(e.getMessage());
            fatal(String.format("Locator form %1$s doesn't appear", this.title));
        }
        info(String.format("Locator of %1$s Form appears", this.title));
    }
}