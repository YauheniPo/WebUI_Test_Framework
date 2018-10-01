package webdriver.elements;

import static com.codeborne.selenide.Selenide.$;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import webdriver.BaseEntity;
import webdriver.driver.Browser;

/**
 * The type Base element.
 */
public abstract class BaseElement extends BaseEntity {
    private SelenideElement element;

    /**
     * Instantiates a new Base element.
     *
     * @param locator the locator
     * @param nameOf  the name of
     */
    BaseElement(final By locator, final String nameOf) {
        super(locator, nameOf);
        LOGGER.info(String.format("Locator of '%1$s' Element", this.title));
    }

    /**
     * Gets element.
     *
     * @return the element
     */
    SelenideElement getElement() {
        waitForIsElementPresent();
        return this.element;
    }

    /**
     * Gets text.
     *
     * @return the text
     */
    public String getText() {
        waitForIsElementPresent();
        LOGGER.info(String.format("Getting text of element %s", this.title));
        return this.element.getText();
    }

    /**
     * Click.
     */
    public void click() {
        waitForIsElementPresent();
        LOGGER.info(String.format("clicking %s", this.title));
        this.element.hover();
        Selenide.executeJavaScript("arguments[0].style.border='3px solid red'", this.element);
        this.element.click();
    }

    /**
     * Wait for is element present.
     */
    void waitForIsElementPresent() {
        boolean isVisible = false;
        if (isPresent(Browser.TIMEOUT_FOR_CONDITION)) {
            try {
                isVisible = $(this.titleLocator).exists();
            } catch (Exception ex) {
                LOGGER.debug(this, ex);
            }
        }
        if (!isVisible) {
            ASSERT_WRAPPER.fatal(String.format("Element %s didn't find", this.title));
        }
    }

    /**
     * Check for is element present on the page.
     *
     * @return Is element present
     */
    public boolean isPresent() {
        return isPresent(Browser.TIMEOUT_FOR_CONDITION);
    }

    /**
     * Is present boolean.
     *
     * @param timeout the timeout
     * @return the boolean
     */
    public boolean isPresent(long timeout) {
        this.element = $(this.titleLocator).waitUntil(Condition.visible, timeout);
        return this.element.isDisplayed();
    }
}