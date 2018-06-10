package webdriver.elements;

import org.openqa.selenium.By;
import webdriver.Browser;

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
		waitAndAssertIsPresent();
		getElement().clear();
		type(value);
	}

	private void type(final String value) {
		waitAndAssertIsPresent();
		info(String.format("text sending" + " '%1$s'", value));
		if (Browser.getDriver() != null) {
			Browser.getDriver().executeScript("arguments[0].style.border='3px solid red'", getElement());
		}
		getElement().sendKeys(value);
	}
}