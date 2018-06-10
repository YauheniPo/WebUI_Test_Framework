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

public abstract class BaseElement extends BaseEntity {
    protected String name;
    private By locator;
    private RemoteWebElement element;

    BaseElement(final By loc, final String nameOf) {
        locator = loc;
        name = nameOf;
    }

    RemoteWebElement getElement() {
        waitForIsElementPresent();
        return element;
    }

    public String getText() {
        waitForIsElementPresent();
        info(String.format("Getting text of element %s", this.name));
        return element.getText();
    }

    public void click() {
        waitForIsElementPresent();
        info(String.format("clicking %s", this.name));
        Browser.getDriver().getMouse().mouseMove(element.getCoordinates());
        if (Browser.getDriver() instanceof JavascriptExecutor) {
            Browser.getDriver().executeScript("arguments[0].style.border='3px solid red'", element);
        }
        element.click();
    }

    private void waitForIsElementPresent() {
        isPresent(Integer.valueOf(Browser.getTimeoutForCondition()));
        try {
            element.isDisplayed();
        } catch (Exception | AssertionError ex) {
            debug(ex.getMessage());
        }
    }

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