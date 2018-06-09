package webdriver.elements;

import org.openqa.selenium.By;
import webdriver.Browser;

public class TextBox extends BaseElement {
	public TextBox(final By locator, final String name) {
		super(locator, name);
	}

	public void setText(final String value) {
		waitAndAssertIsPresent();
		getElement().clear();
		type(value);
	}

	private void type(final String value) {
		waitAndAssertIsPresent();
		info(String.format("locator text typing" + " '%1$s'", value));
		if (Browser.getDriver() != null) {
			Browser.getDriver().executeScript("arguments[0].style.border='3px solid red'", getElement());
		}
		getElement().sendKeys(value);
	}
}