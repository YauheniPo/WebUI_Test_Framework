package webdriver.driver;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Synchronized;
import org.aeonbits.owner.ConfigFactory;
import org.openqa.selenium.WebDriver;
import webdriver.Logger;
import webdriver.reports.listeners.WebEventListener;
import webdriver.resources.IDriverEnvironment;
import webdriver.resources.IStageEnvironment;

import javax.naming.NamingException;

/**
 * The type Browser.
 */
public final class Browser {
    private static final IDriverEnvironment driverEnv = ConfigFactory.create(IDriverEnvironment.class);
    private static final IStageEnvironment stageEnv = ConfigFactory.create(IStageEnvironment.class);
    private static final Browsers CURRENT_BROWSER = Browsers.valueOf((System.getenv("browser") == null
                                                                      ? driverEnv.browser()
                                                                      : System.getenv("browser")).toUpperCase());
    private static final int IMPLICITY_WAIT = driverEnv.implicityWait();
    private static final Logger LOGGER = Logger.getInstance();
    private static Browser instance;
    static final String GRID_URL =driverEnv.gridUrl();
    static final boolean IS_HEADLESS = driverEnv.browserHeadless();
    public static final int TIMEOUT_FOR_CONDITION = driverEnv.defaultConditionTimeout();
    public static final String BROWSER_URL = stageEnv.urlLoginPage();
    public static final int PAGE_WAIT = driverEnv.pageImplicityWait();

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
            initNewDriver();
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
    public static WebDriver getDriver() {
        return WebDriverRunner.getWebDriver();
    }

    /**
     * Open start page.
     */
    public static void openStartPage() {
        navigate(BROWSER_URL);
    }

    /**
     * Gets new driver.
     */
    private static void initNewDriver() {
        Configuration.timeout = IMPLICITY_WAIT;
        try {
            BrowserFactory.setUp(CURRENT_BROWSER);
            WebDriverRunner.addListener(new WebEventListener());
        } catch (NamingException e) {
            LOGGER.debug("Browser: getting New Driver", e);
        }
    }

    /**
     * Refresh.
     */
    public static void refresh() {
        Selenide.refresh();
        LOGGER.info("Page was refreshed.");
    }

    /**
     * Window maximise.
     */
    public void windowMaximise() {
        try {
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
     * The enum Browsers.
     */
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    public enum Browsers {
        FIREFOX("firefox"),
        CHROME("chrome");

        private final String value;
    }
}