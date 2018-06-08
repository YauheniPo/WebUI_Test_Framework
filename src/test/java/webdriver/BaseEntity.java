package webdriver;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import java.io.File;
import java.nio.file.Paths;

public abstract class BaseEntity {
    protected static Logger logger = Logger.getInstance();

    protected Browser getBrowser() {
        return Browser.getInstance();
    }

    @BeforeTest
    public void before() {
        Browser browser = getBrowser();
        browser.windowMaximise();
        browser.navigate(browser.getStartBrowserURL());
    }

    @AfterTest
    public void after() {
        getBrowser().exit();
    }

    protected final void debug(final String message) {
        logger.debug(String.format("[%1$s] %2$s", this.getClass().getSimpleName(), (message)));
    }

    public void doAssert(final Boolean isTrue, final String passMsg, final String failMsg) {
        if (isTrue) {
            info(passMsg);
        } else {
            fatal(failMsg);
        }
    }

    public void assertEquals(final Object expected, final Object actual) {
        if (!expected.equals(actual)) {
            fatal("Expected value: '" + expected + "', but was: '" + actual + "'");
        }
    }

    public void assertEquals(final String message, final Object expected, final Object actual) {
        if (!expected.equals(actual)) {
            fatal(message);
        }
    }

    protected void info(final String message) {
        logger.info(message);
    }

    protected void error(final String message) {
        logger.error(message);
    }

    protected void fatal(final String message) {
        logger.fatal(message);
    }

    protected String makeScreen(final Class<? extends BaseEntity> name) {
        return makeScreen(name, true);
    }

    protected String makeScreen(final Class<? extends BaseEntity> name, final boolean additionalInfo) {
        String fileName = name.getPackage().getName() + "." + name.getSimpleName();
        String pageSourcePath = String.format("surefire-reports" + File.separator + "html" +
                File.separator + "Screenshots/%1$s.txt", fileName);
        String screenshotPath = String.format("surefire-reports" + File.separator + "html" +
                File.separator + "Screenshots/%1$s.png", fileName);

        try {
            String pageSource = getBrowser().getDriver().getPageSource();
            FileUtils.writeStringToFile(new File(Paths.get(pageSourcePath).toUri()), pageSource);
        } catch (Exception e1) {
            logger.debug(e1.getMessage());
        }
        try {
            File screen = getBrowser().getDriver().getScreenshotAs(OutputType.FILE);
            File addedNewFile = new File(new File(screenshotPath).toURI());
            FileUtils.copyFile(screen, addedNewFile);
        } catch (Exception e) {
            logger.debug(e.getMessage());
        }

        if (additionalInfo) {
            String formattedName = String.format(
                    "<a href='Screenshots/%1$s.png'>ScreenShot</a>", fileName);
            String formattedNamePageSource = String.format(
                    "<a href='Screenshots/%1$s.txt'>Page Source</a>", fileName);
            logger.info(formattedName);
            logger.info(formattedNamePageSource);
        }

        return new File(screenshotPath).getAbsolutePath();
    }
}