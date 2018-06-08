package webdriver.elements;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import webdriver.BaseEntity;
import webdriver.Browser;
import webdriver.CommonFunctions;
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

    public void setElement(final RemoteWebElement elementToSet) {
        element = elementToSet;
    }

	public void fetchElementIntoElement(By locator) {
		element = (RemoteWebElement) element.findElement(locator);
	}

	public void fetchElementIntoElementByLocator(By locator) {
		waitAndAssertIsPresent();
		element = (RemoteWebElement) element.findElement(locator);
	}

    public String getName() {
        try {
            if (name == null) {
                name = getBrowser().getDriver().findElement(locator).getText();
            }
        } catch (Exception e) {
            debug(e.getMessage());
            name = "unknown";
        }
        return name;
    }

    public String getText() {
        waitForIsElementPresent();
        return element.getText();
    }

    public void sendKeys(Keys key) {
        waitForIsElementPresent();
        assertIsPresent();
        getBrowser().getDriver().findElement(locator).sendKeys(key);
    }

    public void click() {
        waitForIsElementPresent();
        info("locator clicking");
        getBrowser().getDriver().getMouse().mouseMove(element.getCoordinates());
        if (getBrowser().getDriver() instanceof JavascriptExecutor) {
            getBrowser().getDriver().executeScript("arguments[0].style.border='3px solid red'", element);
        }
        element.click();
    }

    public void clickWithoutWait() {
        info("locator clicking");
        getBrowser().getDriver().getMouse().mouseMove(element.getCoordinates());
        if (getBrowser().getDriver() instanceof JavascriptExecutor) {
            getBrowser().getDriver().executeScript("arguments[0].style.border='3px solid red'", element);
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
        getBrowser().getDriver().getMouse().mouseMove(element.getCoordinates());
        getBrowser().getDriver().getMouse().contextClick(element.getCoordinates());
    }

    public void clickViaAction() {
        waitForIsElementPresent();
        info("locator clicking");
        Actions action = new Actions(getBrowser().getDriver());
        action.click(getElement());
        action.perform();
    }

    public void clickViaJS() {
        waitForIsElementPresent();
        assertIsPresent();
        info("locator clicking");
        getBrowser().getDriver().executeScript("arguments[0].click();", getElement());
    }

    public void doubleClick() {
        waitForIsElementPresent();
        info("locator clicking double");
        getBrowser().getDriver().getMouse().mouseMove(element.getCoordinates());
        Actions builder = new Actions(getBrowser().getDriver());
        Action dClick = builder.doubleClick(element).build();
        dClick.perform();

    }

    public void moveMouseToElement() {
        waitForIsElementPresent();
        CommonFunctions.centerMouse();
        Actions action = new Actions(getBrowser().getDriver());
        action.moveToElement(getElement());
        action.perform();
    }

    public final boolean exists() {
        getBrowser().getDriver().manage().timeouts().implicitlyWait(TIMEOUT_WAIT_0, TimeUnit.SECONDS);
        boolean result = !getBrowser().getDriver().findElements(locator).isEmpty();
        if (result)
            element = (RemoteWebElement) getBrowser().getDriver().findElement(locator);
        getBrowser().getDriver().manage().timeouts().implicitlyWait(Integer.valueOf(getBrowser().getTimeoutForCondition()), TimeUnit.SECONDS);
        return result;
    }

    private void waitForIsElementPresent() {
        isPresent(Integer.valueOf(getBrowser().getTimeoutForCondition()));
        try {
            element.isDisplayed();
        } catch (Exception | AssertionError ex) {
            debug(ex.getMessage());
        }
    }

    public void waitForElementClickable() {
        new WebDriverWait(getBrowser().getDriver(), Long.parseLong(getBrowser().getTimeoutForCondition())).until(ExpectedConditions.elementToBeClickable(locator));
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

    public void waitForDoesNotExist() {
        ExpectedCondition condition = (ExpectedCondition<Boolean>) driver -> Objects.requireNonNull(driver).findElements(locator).isEmpty();
        SmartWait.waitFor(condition);
    }

    public String getAttribute(final String attr) {
        waitForIsElementPresent();
        return getBrowser().getDriver().findElement(locator).getAttribute(attr);
    }

    private void assertIsPresent() {
        if (!isPresent()) {
            fatal("locator is absent");
        }
    }

    public void assertIsAbsent() {
        if (isPresent()) {
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