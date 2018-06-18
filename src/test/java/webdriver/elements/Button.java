package webdriver.elements;

import org.openqa.selenium.By;

/**
 * The type Button.
 */
public class Button extends BaseElement {

    /**
     * Instantiates a new Button.
     *
     * @param locator the locator
     * @param name    the name
     */
    public Button(final By locator, final String name) {
        super(locator, name);
    }
}