package webdriver.elements;

import com.codeborne.selenide.Selenide;
import org.openqa.selenium.By;

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
        type(value);
    }

    /**
     * Type.
     *
     * @param value the value
     */
    private void type(final String value) {
        waitForIsElementPresent();
        Selenide.executeJavaScript("arguments[0].style.border='3px solid red'", getElement());
        getElement().setValue(value);
        LOGGER.info(String.format("text entered: %s", value));
    }
}