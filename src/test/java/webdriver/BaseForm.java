package webdriver;

import org.openqa.selenium.By;

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
        info(String.format("Locator of '%1$s' Form", this.title));
        checkIsDisplayed();
    }
}