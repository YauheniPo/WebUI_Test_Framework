package webdriver.reports.listeners;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.AbstractWebDriverEventListener;
import webdriver.Logger;

public class WebEventListener extends AbstractWebDriverEventListener {
    private static final Logger LOGGER = Logger.getInstance();

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

    public void onException(Throwable error, WebDriver driver) {
        LOGGER.info("Exception occured: " + error);
    }

    @Override
    public void beforeFindBy(By by, WebElement element, WebDriver driver) {
        LOGGER.info("Trying to find Element By : " + by.toString());
    }

    @Override
    public void afterFindBy(By by, WebElement element, WebDriver driver) {
        LOGGER.info("Found Element By : " + by.toString());
    }
}