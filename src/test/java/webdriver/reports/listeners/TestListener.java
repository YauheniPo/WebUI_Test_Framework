package webdriver.reports.listeners;

import com.relevantcodes.extentreports.LogStatus;
import io.qameta.allure.Attachment;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;
import webdriver.Logger;
import webdriver.reports.ExtentManager;
import webdriver.reports.ExtentTestManager;

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
        ExtentTestManager.endTest();
        ExtentManager.getReporter().flush();
    }

    @Override
    public void onTestStart(ITestResult iTestResult) {
        LOGGER.info("I am in onTestStart method " +  getTestMethodName(iTestResult) + " start");
        ExtentTestManager.startTest(iTestResult.getMethod().getMethodName(),"");
    }

    @Override
    public void onTestSuccess(ITestResult iTestResult) {
        LOGGER.info("I am in onTestSuccess method " +  getTestMethodName(iTestResult) + " succeed");
        ExtentTestManager.getTest().log(LogStatus.PASS, "Test passed");
    }

    @Override
    public void onTestSkipped(ITestResult iTestResult) {
        LOGGER.info("I am in onTestSkipped method "+  getTestMethodName(iTestResult) + " skipped");
        ExtentTestManager.getTest().log(LogStatus.SKIP, "Test Skipped");
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {
        LOGGER.info("Test failed but it is in defined success ratio " + getTestMethodName(iTestResult));
    }

    @Override
    public void onTestFailure(ITestResult result) {
        LOGGER.info("Test FAILED: " + result.getName());
        LOGGER.info("I am in onTestFailure method " + getTestMethodName(result) + " failed");
    }
}