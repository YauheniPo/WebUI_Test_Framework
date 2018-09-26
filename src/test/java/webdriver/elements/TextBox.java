package webdriver.elements;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import webdriver.driver.Browser;

/**
 * The type Text box.
 */
public class TextBox extends BaseElement {

    /**
     * Instantiates a new Text box.
     *
     * @param locator the locator
     * @param name    the name
     */
    public TextBox(final By locator, final String name) {
        super(locator, name);
    }

    /**
     * Sets text.
     *
     * @param value the value
     */
    public void setText(final String value) {
        waitForIsElementPresent();
        getElement().clear();
        type(value);
    }

    /**
     * Type.
     *
     * @param value the value
     */
    private void type(final String value) {
        waitForIsElementPresent();
        LOGGER.info(String.format("text sending" + " '%1$s'", value));
        if (Browser.getDriver() instanceof JavascriptExecutor) {
            Browser.getDriver().executeScript("arguments[0].style.border='3px solid red'", getElement());
        }
        getElement().sendKeys(value);
    }
}