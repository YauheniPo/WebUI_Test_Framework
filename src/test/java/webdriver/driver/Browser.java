package webdriver.driver;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Synchronized;
import org.aeonbits.owner.ConfigFactory;
import org.openqa.selenium.remote.RemoteWebDriver;
import webdriver.Logger;
import webdriver.resources.IDriverEnvironment;
import webdriver.resources.IStageEnvironment;

import java.util.concurrent.TimeUnit;
import javax.naming.NamingException;

/**
 * The type Browser.
 */
public final class Browser {
    private static IDriverEnvironment driverEnv = ConfigFactory.create(IDriverEnvironment.class);
    private static IStageEnvironment stageEnv = ConfigFactory.create(IStageEnvironment.class);
    private static final Browsers CURRENT_BROWSER = Browsers.valueOf(driverEnv.browser().toUpperCase());
    private static final int IMPLICITY_WAIT = driverEnv.implicityWait();
    private static final Logger LOGGER = Logger.getInstance();
    private static Browser instance;
    private volatile static RemoteWebDriver driver;
    @Getter private static String browserURL = stageEnv.urlLoginPage();
    @Getter private static boolean isDriverManager = driverEnv.driverManager();
    @Getter private static String gridUrl =driverEnv.gridUrl();
    @Getter private static boolean isHeadless = driverEnv.browserHeadless();
    @Getter private static int timeoutForCondition = driverEnv.defaultConditionTimeout();

    /**
     * Instantiates a new Browser.
     */
    private Browser() {
        LOGGER.info(String.format("browser %s is ready", CURRENT_BROWSER.name()));
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    @Synchronized
    public static Browser getInstance() {
        if (instance == null) {
            driver = getNewDriver();
            instance = new Browser();
        }
        return instance;
    }

    /**
     * Is grid boolean.
     *
     * @return the boolean
     */
    static boolean isGrid() {
        return driverEnv.grid();
    }

    /**
     * Gets browser name.
     *
     * @return the browser name
     */
    public static String getBrowserName() {
        return CURRENT_BROWSER.name();
    }

    /**
     * Gets driver.
     *
     * @return the driver
     */
    public static RemoteWebDriver getDriver() {
        if (driver == null) {
            driver = getNewDriver();
        }
        return driver;
    }

    /**
     * Open start page.
     */
    public static void openStartPage() {
        getDriver().navigate().to(browserURL);
    }

    /**
     * Gets new driver.
     *
     * @return the new driver
     */
    private static RemoteWebDriver getNewDriver() {
        try {
            RemoteWebDriver driver = BrowserFactory.setUp(CURRENT_BROWSER.toString());
            driver.manage().timeouts().implicitlyWait(IMPLICITY_WAIT, TimeUnit.SECONDS);
            LOGGER.info("browser constructed");
            return driver;
        } catch (NamingException e) {
            LOGGER.debug("Browser: getting New Driver", e);
        }
        return null;
    }

    /**
     * Refresh.
     */
    public void refresh() {
        getDriver().navigate().refresh();
        LOGGER.info("Page was refreshed.");
    }

    /**
     * Window maximise.
     */
    public void windowMaximise() {
        try {
            getDriver().executeScript("if (window.screen) {window.moveTo(0, 0);window.resizeTo(window.screen.availWidth,window.screen.availHeight);};");
            getDriver().manage().window().maximize();
        } catch (Exception e) {
            LOGGER.debug(e);
        }
    }

    /**
     * Navigate.
     *
     * @param url the url
     */
    public void navigate(final String url) {
        getDriver().navigate().to(url);
    }

    /**
     * Exit.
     */
    public void exit() {
        try {
            getDriver().quit();
            instance = null;
            LOGGER.info("browser driver quit");
        } catch (Exception e) {
            LOGGER.debug(e.getMessage());
        }
    }

    /**
     * The enum Browsers.
     */
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    public enum Browsers {
        FIREFOX("firefox"),
        CHROME("chrome");

        private final String value;
    }
}