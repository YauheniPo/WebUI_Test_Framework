package webdriver.waitings;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import webdriver.driver.Browser;
import webdriver.Logger;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

/**
 * The type Smart wait.
 */
final public class SmartWait {
    private static final Logger LOGGER = Logger.getInstance();

    /**
     * Wait for t.
     *
     * @param <T>              the type parameter
     * @param condition        the condition
     * @param timeOutInSeconds the time out in seconds
     *
     * @return the t
     */
    private static <T> T waitFor(ExpectedCondition<T> condition, long timeOutInSeconds) {
        Browser.getDriver().manage().timeouts().implicitlyWait(0L, TimeUnit.SECONDS);
        Wait<WebDriver> wait = new FluentWait<>((WebDriver) Browser.getDriver())
            .withTimeout(Duration.ofSeconds(timeOutInSeconds)).pollingEvery(Duration.ofMillis(300));
        try {
            return wait.until(condition);
        } catch (Exception e) {
            LOGGER.debug("SmartWait.waitFor", e);
        } finally {
            Browser.getDriver().manage().timeouts().implicitlyWait(Browser.getTimeoutForCondition(), TimeUnit.SECONDS);
        }
        return null;
    }

    /**
     * Wait for true boolean.
     *
     * @param condition        the condition
     * @param timeOutInSeconds the time out in seconds
     *
     * @return the boolean
     */
    public static boolean waitForTrue(ExpectedCondition<Boolean> condition, long timeOutInSeconds) {
        try {
            return waitFor(condition, timeOutInSeconds) != null;
        } catch (Exception e) {
            LOGGER.debug("Wait waitForTrue", e);
            return false;
        }
    }
}