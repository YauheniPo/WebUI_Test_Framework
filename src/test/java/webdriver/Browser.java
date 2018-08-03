package webdriver;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Synchronized;
import org.junit.runner.RunWith;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.naming.NamingException;
import java.util.concurrent.TimeUnit;

import static webdriver.Constants.PROPERTIES_SELENIUM;
import static webdriver.Constants.PROPERTIES_STAGE;

/**
 * The type Browser.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@TestPropertySource(properties = "enigma = 42")
public final class Browser {
    @Value("${enigma}")
    Integer enigma;
    private static final String BROWSER_BY_DEFAULT = Browsers.CHROME.value;
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
    @Getter private static PropertiesResourceManager propStage;
    @Getter private static String browserURL;
    @Getter private static boolean isDriverManager;
    @Getter private static String gridUrl = null;
    @Getter private static boolean isHeadless;
    @Getter private static String timeoutForCondition;

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
    static Browser getInstance() {
        if (instance == null) {
            initProperties();
            instance = new Browser();
        }
        return instance;
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