package webdriver;

import org.openqa.selenium.remote.RemoteWebDriver;

import javax.naming.NamingException;
import java.util.concurrent.TimeUnit;

import static webdriver.Constants.PROPERTIES_SELENIUM;
import static webdriver.Constants.PROPERTIES_STAGE;

/**
 * The type Browser.
 */
public final class Browser {
    private static final String BROWSER_BY_DEFAULT = Browsers.FIREFOX.value;
    private static final String URL_LOGIN_PAGE = "urlLoginPage";
    private static final String DRIVER_MANAGER = "driverManager";
    private static final String BROWSER_HEADLESS = "browserHeadless";
    private static final String BROWSER_PROP = "browser";
    private static final String STAGE = "stage";
    private static final String GRID = "grid";
    private static final String GRID_URL = "gridUrl";
    private static final String GRID_IP = "gridIp";
    private static final String GRID_PORT = "gridPort";
    private static final PropertiesResourceManager PROPS = new PropertiesResourceManager(PROPERTIES_SELENIUM);
    private static final Browsers CURRENT_BROWSER = Browsers.valueOf((System.getenv("browser") == null
                                                                     ? System.getProperty(BROWSER_PROP, PROPS.getProperty(BROWSER_PROP, BROWSER_BY_DEFAULT))
                                                                     : System.getenv("browser")).toUpperCase());
    private static final long IMPLICITY_WAIT = Long.parseLong(PROPS.getProperty("implicityWait", String.valueOf(10)));
    private static final String DEFAULT_CONDITION_TIMEOUT = "defaultConditionTimeout";
    private static final Logger LOGGER = Logger.getInstance();
    private static Browser instance;
    private volatile static RemoteWebDriver driver;
    private static PropertiesResourceManager propStage;
    private static String browserURL;
    private static boolean isDriverManager;
    private static String gridUrl = null;
    private static boolean isHeadless;
    private static String timeoutForCondition;

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
    synchronized static Browser getInstance() {
        if (instance == null) {
            initProperties();
            instance = new Browser();
        }
        return instance;
    }

    /**
     * Is driver manager boolean.
     *
     * @return the boolean
     */
    static boolean isDriverManager() {
        return isDriverManager;
    }

    /**
     * Is browser headless boolean.
     *
     * @return the boolean
     */
    static boolean isBrowserHeadless() {
        return isHeadless;
    }

    /**
     * Is Grid URL.
     *
     * @return the boolean
     */
    static String getGridUrl() {
        return gridUrl;
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
     * Gets PROPS stage.
     *
     * @return the PROPS stage
     */
    public static PropertiesResourceManager getPropsStage() {
        return propStage;
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
     * Gets timeout for condition.
     *
     * @return the timeout for condition
     */
    public static String getTimeoutForCondition() {
        return timeoutForCondition;
    }

    /**
     * Open start page.
     */
    public static void openStartPage() {
        getDriver().navigate().to(browserURL);
    }

    /**
     * Init properties.
     */
    private static void initProperties() {
        isDriverManager = Boolean.valueOf((System.getenv("driver_manager") == null
                                           ? PROPS.getProperty(DRIVER_MANAGER, "false")
                                           : System.getenv("driver_manager")));
        isHeadless = Boolean.valueOf(PROPS.getProperty(BROWSER_HEADLESS, "false"));
        if (Boolean.valueOf(PROPS.getProperty(GRID, "false"))) {
            gridUrl = String.format(PROPS.getProperty(GRID_URL), PROPS.getProperty(GRID_IP, "localhost"), PROPS.getProperty(GRID_PORT));
        }
        timeoutForCondition = PROPS.getProperty(DEFAULT_CONDITION_TIMEOUT);
        propStage = new PropertiesResourceManager(PROPERTIES_STAGE);
        String choosenStage = propStage.getProperty(STAGE);
        browserURL = String.format(propStage.getProperty(URL_LOGIN_PAGE), choosenStage);
        driver = getNewDriver();
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
     * Gets start browser url.
     *
     * @return the start browser url
     */
    static String getStartBrowserURL() {
        return browserURL;
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
    void windowMaximise() {
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
    void navigate(final String url) {
        getDriver().navigate().to(url);
    }

    /**
     * Exit.
     */
    void exit() {
        try {
            getDriver().quit();
            LOGGER.info("browser driver quit");
        } catch (Exception e) {
            LOGGER.debug(e.getMessage());
        }
    }

    /**
     * The enum Browsers.
     */
    public enum Browsers {
        FIREFOX("firefox"),
        CHROME("chrome");

        private final String value;

        /**
         * Instantiates a new Browsers.
         *
         * @param values the values
         */
        Browsers(final String values) {
            value = values;
        }
    }
}