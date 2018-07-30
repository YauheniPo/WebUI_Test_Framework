package webdriver.elements;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import webdriver.BaseEntity;
import webdriver.Browser;
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
        info(String.format("Locator of '%1$s' Element", this.title));
    }

    /**
     * Gets element.
     *
     * @return the element
     */
    RemoteWebElement getElement() {
        waitForIsElementPresent();
        return element;
    }

    /**
     * Gets text.
     *
     * @return the text
     */
    public String getText() {
        waitForIsElementPresent();
        info(String.format("Getting text of element %s", this.title));
        return element.getText();
    }

    /**
     * Click.
     */
    public void click() {
        waitForIsElementPresent();
        info(String.format("clicking %s", this.title));
        Browser.getDriver().getMouse().mouseMove(element.getCoordinates());
        if (Browser.getDriver() instanceof JavascriptExecutor) {
            Browser.getDriver().executeScript("arguments[0].style.border='3px solid red'", element);
        }
        element.click();
    }

    /**
     * Wait for is element present.
     */
    public void waitForIsElementPresent() {
        boolean isVisible = false;
        if (isPresent(Integer.parseInt(Browser.getTimeoutForCondition()))) {
            try {
                isVisible = element.isDisplayed();
            } catch (Exception ex) {
                LOGGER.debug(this, ex);
            }
        }
        if (!isVisible) {
            ASSERT_WRAPPER.fatal(String.format("Element %s didn't find", title));
        }
    }

    /**
     * Check for is element present on the page.
     *
     * @return Is element present
     */
    public boolean isPresent() {
        return isPresent(Long.parseLong(Browser.getTimeoutForCondition()));
    }

    /**
     * Is present boolean.
     *
     * @param timeout the timeout
     * @return the boolean
     */
    private boolean isPresent(long timeout) {
        ExpectedCondition<Boolean> condition = driver -> {
            try {
                List<WebElement> elems = Objects.requireNonNull(driver).findElements(this.titleLocator);
                for (WebElement elem : elems) {
                    if (elem.isDisplayed()) {
                        element = (RemoteWebElement) elem;
                        return true;
                    }
                }
                return false;
            } catch (Exception e) {
                debug(e.getMessage());
                return false;
            }
        };
        return SmartWait.waitForTrue(condition, timeout);
    }
}