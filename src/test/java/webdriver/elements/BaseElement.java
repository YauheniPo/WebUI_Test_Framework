package webdriver.elements;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import webdriver.BaseEntity;
import webdriver.driver.Browser;
import webdriver.waitings.SmartWait;

import java.util.List;
import java.util.Objects;

/**
 * The type Base element.
 */
public abstract class BaseElement extends BaseEntity {
    private RemoteWebElement element;

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
    RemoteWebElement getElement() {
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
        Browser.getDriver().getMouse().mouseMove(this.element.getCoordinates());
        Browser.getDriver().executeScript("arguments[0].style.border='3px solid red'", this.element);
        this.element.click();
    }

    /**
     * Wait for is element present.
     */
    void waitForIsElementPresent() {
        boolean isVisible = false;
        if (isPresent(Browser.TIMEOUT_FOR_CONDITION)) {
            try {
                isVisible = this.element.isDisplayed();
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
        ExpectedCondition<Boolean> condition = driver -> {
            try {
                List<WebElement> elems = Objects.requireNonNull(driver).findElements(this.titleLocator);
                for (WebElement elem : elems) {
                    if (elem.isDisplayed()) {
                        this.element = (RemoteWebElement) elem;
                        return true;
                    }
                }
                return false;
            } catch (Exception e) {
                LOGGER.debug(e.getMessage());
                return false;
            }
        };
        return SmartWait.waitForTrue(condition, timeout);
    }
}