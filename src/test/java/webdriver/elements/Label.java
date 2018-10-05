package webdriver.elements;

import org.openqa.selenium.By;

/**
 * The type Label.
 */
public class Label extends BaseElement {

    /**
     * Instantiates a new Label.
     *
     * @param locator the locator
     * @param name    the name
     */
    public Label(final By locator, final String name) {
        super(locator, name);
    }

    /**
     * Is contains text boolean.
     *
     * @param text the text
     *
     * @return the boolean
     */
    public boolean isContainsText(String text) {
        return getElement().getText().contains(text);
    }
}