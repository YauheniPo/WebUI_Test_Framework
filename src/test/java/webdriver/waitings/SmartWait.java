package webdriver.waitings;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import webdriver.Browser;
import webdriver.Logger;

import java.util.concurrent.TimeUnit;

final public class SmartWait {
    private static final Logger logger = Logger.getInstance();

    private static <T> T waitFor(ExpectedCondition<T> condition, long timeOutInSeconds) {
        Browser.getDriver().manage().timeouts().implicitlyWait(0L, TimeUnit.MILLISECONDS);
        Wait<WebDriver> wait = new FluentWait<>((WebDriver) Browser.getDriver())
                .withTimeout(timeOutInSeconds, TimeUnit.SECONDS)
                .pollingEvery(300, TimeUnit.MILLISECONDS);
        try {
            return wait.until(condition);
        } catch (Exception | AssertionError e) {
            logger.debug("SmartWait.waitFor", e);
        } finally {
            Browser.getDriver().manage().timeouts().implicitlyWait(Long.parseLong(Browser.getTimeoutForCondition()), TimeUnit.SECONDS);
        }
        return null;
    }

    public static boolean waitForTrue(ExpectedCondition<Boolean> condition, long timeOutInSeconds) {
        try {
            return Boolean.class.cast(waitFor(condition, timeOutInSeconds));
        } catch (Exception e) {
            logger.debug("SmartWait.waitForTrue", e);
            return false;
        }
    }
}