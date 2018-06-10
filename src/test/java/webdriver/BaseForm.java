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
        init(locator, formTitle);
        assertIsOpen();
    }

    private void init(final By locator, final String formTitle) {
        titleLocator = locator;
        title = formTitle;
        info(String.format("locator form '%1$s'", this.title));
    }

    private void assertIsOpen() {
        Label elem = new Label(titleLocator, title);
        try {
            elem.waitAndAssertIsPresent();
        } catch (Exception | AssertionError e) {
            debug(e.getMessage());
            fatal(String.format("locator form %1$s doesn't appears", this.title));
        }
    }
}