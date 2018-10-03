package webdriver.reports.listeners;

import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;
import webdriver.driver.Browser;
import webdriver.Logger;

import java.util.Objects;

public class TestListener extends TestListenerAdapter {
    private static final Logger LOGGER = Logger.getInstance();

    private static String getTestMethodName(ITestResult iTestResult) {
        return iTestResult.getMethod().getConstructorOrMethod().getName();
    }

    @Attachment(value = "Text Log", type = "text/plain")
    public static String saveTextLog (String message) {
        return message;
    }

    @Attachment(value = "Attachment HTML", type = "text/html")
    public static String attachHtml(String html) {
        return html;
    }

    @Override
    public void onStart(ITestContext iTestContext) {
        LOGGER.info("I am in onStart method " + iTestContext.getName());
    }

    @Override
    public void onFinish(ITestContext iTestContext) {
        LOGGER.info("I am in onFinish method " + iTestContext.getName());
    }

    @Override
    public void onTestStart(ITestResult iTestResult) {
        LOGGER.info("I am in onTestStart method " +  getTestMethodName(iTestResult) + " start");
    }

    @Override
    public void onTestSuccess(ITestResult iTestResult) {
        LOGGER.info("I am in onTestSuccess method " +  getTestMethodName(iTestResult) + " succeed");
    }

    @Override
    public void onTestSkipped(ITestResult iTestResult) {
        LOGGER.info("I am in onTestSkipped method "+  getTestMethodName(iTestResult) + " skipped");
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {
        LOGGER.info("Test failed but it is in defined success ratio " + getTestMethodName(iTestResult));
    }

    @Override
    public void onTestFailure(ITestResult result) {
        LOGGER.info("Test FAILED: " + result.getName());
        if (Browser.getDriver().getSessionId() != null) {
            LOGGER.info("I am in onTestFailure method " + getTestMethodName(result) + " failed");
            WebDriver driver = Browser.getDriver();
            if (driver != null) {
                LOGGER.info("Screenshot captured for test case:" + getTestMethodName(result));
                makeScreenshot();
            }
            makeExtentReportScreenshot();
        }
    }

    @Attachment(value = "Page screenshot", type = "image/png")
    public byte[] makeScreenshot() {
        return Browser.getDriver().getScreenshotAs(OutputType.BYTES);
    }

    public void makeExtentReportScreenshot() {
        String base64Screenshot = "data:image/png;base64," + Objects.requireNonNull(Browser.getDriver()).getScreenshotAs(OutputType.BASE64);
    }
}