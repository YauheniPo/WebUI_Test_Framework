package webdriver;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import java.io.File;
import java.nio.file.Paths;

public abstract class BaseEntity {
    protected static Logger logger = Logger.getInstance();

    private Browser getBrowser() {
        return Browser.getInstance();
    }

    @BeforeTest
    public void before() {
        Browser browser = getBrowser();
        browser.windowMaximise();
        browser.navigate(Browser.getStartBrowserURL());
    }

    @AfterTest
    public void after() {
        getBrowser().exit();
    }

    protected void assertEquals(final Object expected, final Object actual) {
        if (!expected.equals(actual)) {
            fatal("Expected value: '" + expected.toString() + "', but was: '" + actual.toString() + "'");
        }
    }

    protected final void debug(final String message) {
        logger.debug(String.format("[%1$s] %2$s", this.getClass().getSimpleName(), (message)));
    }

    protected void info(final String message) {
        logger.info(message);
    }

    protected void fatal(final String message) {
        logger.fatal(message);
    }

    void makeScreen(final Class<? extends BaseEntity> name) {
        String fileName = name.getPackage().getName() + "." + name.getSimpleName();
        String pageSourcePath = String.format("surefire-reports" + File.separator + "html" +
                File.separator + "Screenshots/%1$s.txt", fileName);
        String screenshotPath = String.format("surefire-reports" + File.separator + "html" +
                File.separator + "Screenshots/%1$s.png", fileName);
        try {
            String pageSource = Browser.getDriver().getPageSource();
            FileUtils.writeStringToFile(new File(Paths.get(pageSourcePath).toUri()), pageSource);
        } catch (Exception e1) {
            logger.debug(e1.getMessage());
        }
        try {
            File screen = Browser.getDriver().getScreenshotAs(OutputType.FILE);
            File addedNewFile = new File(new File(screenshotPath).toURI());
            FileUtils.copyFile(screen, addedNewFile);
        } catch (Exception e) {
            logger.debug(e.getMessage());
        }
        String formattedName = String.format("'Screenshots/%1$s.png'>ScreenShot", fileName);
        String formattedNamePageSource = String.format("'Screenshots/%1$s.txt'>Page Source", fileName);
        logger.info(formattedName);
        logger.info(formattedNamePageSource);
    }
}