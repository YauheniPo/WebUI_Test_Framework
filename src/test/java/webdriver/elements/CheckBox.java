package webdriver.elements;

import org.openqa.selenium.By;

/**
 * The type Check box.
 */
public class CheckBox extends BaseElement {

    /**
     * Instantiates a new Check box.
     *
     * @param locator the locator
     * @param name    the name
     */
    public CheckBox(final By locator, final String name) {
        super(locator, name);
    }
}