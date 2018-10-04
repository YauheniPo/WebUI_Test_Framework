package webdriver.reports.listeners;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.WebDriverEventListener;
import webdriver.Logger;

public class WebEventListener implements WebDriverEventListener {
    private static final Logger LOGGER = Logger.getInstance();

    @Override
    public void beforeAlertAccept(WebDriver webDriver) {
        LOGGER.info("");
    }

    @Override
    public void afterAlertAccept(WebDriver webDriver) {
        LOGGER.info("");
    }

    @Override
    public void afterAlertDismiss(WebDriver webDriver) {
        LOGGER.info("");
    }

    @Override
    public void beforeAlertDismiss(WebDriver webDriver) {
        LOGGER.info("");
    }

    @Override
    public void beforeNavigateTo(String url, WebDriver driver) {
        LOGGER.info("Before navigating to: '" + url + "'");
    }

    @Override
    public void afterNavigateTo(String url, WebDriver driver) {
        LOGGER.info("Navigated to:'" + url + "'");
    }

    @Override
    public void beforeClickOn(WebElement element, WebDriver driver) {
        LOGGER.info("Trying to click on: " + element.toString());
    }

    @Override
    public void afterClickOn(WebElement element, WebDriver driver) {
        LOGGER.info("Clicked on: " + element.toString());
    }

    @Override
    public void beforeChangeValueOf(WebElement webElement, WebDriver webDriver, CharSequence[] charSequences) {
        LOGGER.info("");
    }

    @Override
    public void afterChangeValueOf(WebElement webElement, WebDriver webDriver, CharSequence[] charSequences) {
        LOGGER.info("");
    }

    @Override
    public void beforeNavigateBack(WebDriver driver) {
        LOGGER.info("Navigating back to previous page");
    }

    @Override
    public void afterNavigateBack(WebDriver driver) {
        LOGGER.info("Navigated back to previous page");
    }

    @Override
    public void beforeNavigateForward(WebDriver driver) {
        LOGGER.info("Navigating forward to next page");
    }

    @Override
    public void afterNavigateForward(WebDriver driver) {
        LOGGER.info("Navigated forward to next page");
    }

    @Override
    public void beforeNavigateRefresh(WebDriver webDriver) {
        LOGGER.info("");
    }

    @Override
    public void afterNavigateRefresh(WebDriver webDriver) {
        LOGGER.info("");
    }

    public void onException(Throwable error, WebDriver driver) {
        LOGGER.info("Exception occured: " + error);
    }

    @Override
    public <X> void beforeGetScreenshotAs(OutputType<X> outputType) {
        LOGGER.info("");
    }

    @Override
    public <X> void afterGetScreenshotAs(OutputType<X> outputType, X x) {
        LOGGER.info("");
    }

    @Override
    public void beforeGetText(WebElement webElement, WebDriver webDriver) {
        LOGGER.info("");
    }

    @Override
    public void afterGetText(WebElement webElement, WebDriver webDriver, String s) {
        LOGGER.info("");
    }

    @Override
    public void beforeFindBy(By by, WebElement element, WebDriver driver) {
        LOGGER.info("Trying to find Element By : " + by.toString());
    }

    @Override
    public void afterFindBy(By by, WebElement element, WebDriver driver) {
        LOGGER.info("Found Element By : " + by.toString());
    }

    @Override
    public void beforeScript(String script, WebDriver driver) {
        LOGGER.info("");
    }

    @Override
    public void afterScript(String script, WebDriver driver) {
        LOGGER.info("");
    }

    @Override
    public void beforeSwitchToWindow(String s, WebDriver webDriver) {
        LOGGER.info("");
    }

    @Override
    public void afterSwitchToWindow(String s, WebDriver webDriver) {
        LOGGER.info("");
    }

}