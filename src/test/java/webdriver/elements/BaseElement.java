package webdriver.elements;

import org.junit.Assert;
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
    private final String name;
    private final By locator;
    private RemoteWebElement element;

    /**
     * Instantiates a new Base element.
     *
     * @param locator the locator
     * @param nameOf  the name of
     */
    BaseElement(final By locator, final String nameOf) {
        this.locator = locator;
        this.name = nameOf;
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
        info(String.format("Getting text of element %s", this.name));
        return element.getText();
    }

    /**
     * Click.
     */
    public void click() {
        waitForIsElementPresent();
        info(String.format("clicking %s", this.name));
        Browser.getDriver().getMouse().mouseMove(element.getCoordinates());
        if (Browser.getDriver() instanceof JavascriptExecutor) {
            Browser.getDriver().executeScript("arguments[0].style.border='3px solid red'", element);
        }
        element.click();
    }

    /**
     * Wait for is element present.
     */
    private void waitForIsElementPresent() {
        if (isPresent(Integer.parseInt(Browser.getTimeoutForCondition()))) {
            boolean isVisible;
            try {
                isVisible = element.isDisplayed();
            } catch (Exception | AssertionError ex) {
                logger.debug(this, ex);
                isVisible = false;
            }
            Assert.assertTrue("Locator is absent", isVisible);
        } else {
            fatal(String.format("Element %s didn't find", name));
        }
    }

    /**
     * Wait and assert is present.
     */
    public void waitAndAssertIsPresent() {
        waitForIsElementPresent();
    }

    private boolean isPresent(long timeout) {
        ExpectedCondition<Boolean> condition = driver -> {
            try {
                List<WebElement> elems = Objects.requireNonNull(driver).findElements(locator);
                for (WebElement elem : elems) {
                    if (elem.isDisplayed()) {
                        element = (RemoteWebElement) elem;
                        return true;
                    }
                }
                return false;
            } catch (Exception | AssertionError e) {
                debug(e.getMessage());
                return false;
            }
        };
        return SmartWait.waitForTrue(condition, timeout);
    }
}