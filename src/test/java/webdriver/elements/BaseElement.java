package webdriver.elements;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import webdriver.BaseEntity;
import webdriver.Browser;
import webdriver.waitings.SmartWait;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public abstract class BaseElement extends BaseEntity {
    private static final int TIMEOUT_WAIT_0 = 0;
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

    public void sendKeys(Keys key) {
        waitForIsElementPresent();
        assertIsPresent();
        Browser.getDriver().findElement(locator).sendKeys(key);
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

    public void clickIfPresent() {
        if (isPresent()) {
            click();
        }
    }

    public void clickRight() {
        waitForIsElementPresent();
        info("Clicking Right");
        Browser.getDriver().getMouse().mouseMove(element.getCoordinates());
        Browser.getDriver().getMouse().contextClick(element.getCoordinates());
    }

    public void doubleClick() {
        waitForIsElementPresent();
        info("locator clicking double");
        Browser.getDriver().getMouse().mouseMove(element.getCoordinates());
        Actions builder = new Actions(Browser.getDriver());
        Action dClick = builder.doubleClick(element).build();
        dClick.perform();

    }

    public final boolean exists() {
        Browser.getDriver().manage().timeouts().implicitlyWait(TIMEOUT_WAIT_0, TimeUnit.SECONDS);
        boolean result = !Browser.getDriver().findElements(locator).isEmpty();
        if (result)
            element = (RemoteWebElement) Browser.getDriver().findElement(locator);
        Browser.getDriver().manage().timeouts().implicitlyWait(Integer.valueOf(Browser.getTimeoutForCondition()), TimeUnit.SECONDS);
        return result;
    }

    private void waitForIsElementPresent() {
        isPresent(Integer.valueOf(Browser.getTimeoutForCondition()));
        try {
            element.isDisplayed();
        } catch (Exception | AssertionError ex) {
            debug(ex.getMessage());
        }
    }

    public void waitForElementClickable() {
        new WebDriverWait(Browser.getDriver(), Long.parseLong(Browser.getTimeoutForCondition())).until(ExpectedConditions.elementToBeClickable(locator));
    }

    public void waitAndAssertIsPresent() {
        waitForIsElementPresent();
    }

    public void waitForExists() {
        ExpectedCondition condition = (ExpectedCondition<Boolean>) driver -> {
            Boolean isExist = !Objects.requireNonNull(driver).findElements(locator).isEmpty();
            if (isExist) {
                element = (RemoteWebElement) driver.findElement(locator);
            }
            return isExist;
        };
        SmartWait.waitFor(condition);
    }

    private void assertIsPresent() {
        if (!isPresent()) {
            fatal("locator is absent");
        }
    }

    private boolean isPresent() {
        return isPresent(Long.parseLong(Browser.getTimeoutForCondition()));
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