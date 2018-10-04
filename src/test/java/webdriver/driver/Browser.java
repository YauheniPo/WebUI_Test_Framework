package webdriver.driver;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
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
    private static final IDriverEnvironment driverEnv = ConfigFactory.create(IDriverEnvironment.class);
    private static final IStageEnvironment stageEnv = ConfigFactory.create(IStageEnvironment.class);
    private static final Browsers CURRENT_BROWSER = Browsers.valueOf(driverEnv.browser().toUpperCase());
    private static final int IMPLICITY_WAIT = driverEnv.implicityWait();
    private static final int PAGE_IMPLICITY_WAIT = driverEnv.pageImplicityWait();
    private static final Logger LOGGER = Logger.getInstance();
    private static Browser instance = new Browser();
    private static ThreadLocal<RemoteWebDriver> driverHolder = ThreadLocal.withInitial(Browser::getNewDriver);
    static final boolean IS_HEADLESS = driverEnv.browserHeadless();
    static final String GRID_URL =driverEnv.gridUrl();
    public static final int TIMEOUT_FOR_CONDITION = driverEnv.defaultConditionTimeout();
    public static final String BROWSER_URL = stageEnv.urlLoginPage();

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
    public static Browser getInstance() {
        if (instance == null) {
            synchronized (Browser.class) {
                if (instance == null) {
                    instance = new Browser();
                }
            }
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
        if (driverHolder.get() == null) {
            driverHolder.set(getNewDriver());
        }
        return driverHolder.get();
    }

    /**
     * Open start page.
     */
    public static void openStartPage() {
        navigate(BROWSER_URL);
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
            driver.manage().timeouts().pageLoadTimeout(PAGE_IMPLICITY_WAIT, TimeUnit.SECONDS);
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
    public static void refresh() {
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
    public static void navigate(final String url) {
        getDriver().navigate().to(url);
    }

    /**
     * Exit.
     */
    public void exit() {
        try {
            getDriver().quit();
            LOGGER.info("browser driver quit");
        } catch (Exception e) {
            LOGGER.debug(e.getMessage());
        } finally {
            driverHolder.set(null);
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