package webdriver;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import webdriver.common.SoftAssertWrapper;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.Paths;

/**
 * The type Base entity.
 */
public abstract class BaseEntity {
    protected static Logger LOGGER = Logger.getInstance();
    protected static SoftAssertWrapper assertWrapper = SoftAssertWrapper.getInstance();

    /**
     * Gets browser.
     *
     * @return the browser
     */
    private Browser getBrowser() {
        return Browser.getInstance();
    }

    /**
     * Before.
     */
    @BeforeTest
    public void before() {
        Browser browser = getBrowser();
        browser.windowMaximise();
        browser.navigate(Browser.getBrowserURL());
    }

    /**
     * After.
     */
    @AfterTest
    public void after() {
        getBrowser().exit();
    }

    /**
     * Debug.
     *
     * @param message the message
     */
    protected final void debug(final String message) {
        LOGGER.debug(String.format("[%1$s] %2$s", this.getClass().getSimpleName(), (message)));
    }

    /**
     * Info.
     *
     * @param message the message
     */
    protected void info(final String message) {
        LOGGER.info(message);
    }

    /**
     * Error.
     *
     * @param message the message
     */
    protected void error(final String message) {
        LOGGER.error(message);
    }

    /**
     * Make screen.
     *
     * @param name the name
     */
    void makeScreen(final Class<? extends BaseEntity> name) {
        String fileName = name.getPackage().getName() + "." + name.getSimpleName();
        String pageSourcePath = String.format("surefire-reports" + File.separator + "html" +
                File.separator + "Screenshots/%1$s.txt", fileName);
        String screenshotPath = String.format("surefire-reports" + File.separator + "html" +
                File.separator + "Screenshots/%1$s.png", fileName);
        try {
            String pageSource = Browser.getDriver().getPageSource();
            FileUtils.writeStringToFile(new File(Paths.get(pageSourcePath).toUri()), pageSource, Charset.defaultCharset());
        } catch (Exception e1) {
            LOGGER.debug(e1.getMessage());
        }
        try {
            File screen = Browser.getDriver().getScreenshotAs(OutputType.FILE);
            File addedNewFile = new File(new File(screenshotPath).toURI());
            FileUtils.copyFile(screen, addedNewFile);
        } catch (Exception e) {
            LOGGER.debug(e.getMessage());
        }
        String formattedName = String.format("'Screenshots/%1$s.png'>ScreenShot", fileName);
        String formattedNamePageSource = String.format("'Screenshots/%1$s.txt'>Page Source", fileName);
        LOGGER.info(formattedName);
        LOGGER.info(formattedNamePageSource);
    }
}