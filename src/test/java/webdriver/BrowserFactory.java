package webdriver;

import io.github.bonigarcia.wdm.ChromeDriverManager;
import io.github.bonigarcia.wdm.FirefoxDriverManager;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import webdriver.Browser.Browsers;

import javax.naming.NamingException;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

import static webdriver.ConstantsFrm.*;

/**
 * The type Browser factory.
 */
final public class BrowserFactory {
    private static final String PLATFORM = System.getProperty("os.name").toLowerCase();
    private static final Logger logger = Logger.getInstance();
    private static final String CLS_NAME = BrowserFactory.class.getName();

    /**
     * Instantiates a new Browser factory.
     */
    private BrowserFactory() {
        // do not instantiate BrowserFactory class
    }

    /**
     * Sets up.
     *
     * @param type the type
     *
     * @return the up
     */
    public static RemoteWebDriver setUp(final Browsers type) {
        return getWebDriver(type);
    }

    /**
     * Sets up.
     *
     * @param type the type
     *
     * @return the up
     *
     * @throws NamingException the naming exception
     */
    public static RemoteWebDriver setUp(final String type) throws NamingException {
        for (Browsers t : Browsers.values()) {
            if (t.toString().equalsIgnoreCase(type)) {
                return setUp(t);
            }
        }
        throw new NamingException("browser name wrong" + ":\nchrome\nfirefox\niexplore\nopera\nsafari");
    }

    /**
     * Gets web driver.
     *
     * @param type the type
     *
     * @return the web driver
     */
    private static RemoteWebDriver getWebDriver(final Browsers type) {
        boolean isGrid = Browser.getGridUrl() != null;
        switch (type) {
            case CHROME:
                if (isGrid)
                    return getGridRemoteDriver(DesiredCapabilities.chrome());
                if (Browser.isDriverManager()) {
                    ChromeDriverManager.getInstance().setup();
                    return new ChromeDriver(getChromeOptionsHeadless());
                }
                return getChromeDriver();
            case FIREFOX:
                if (isGrid)
                    return getGridRemoteDriver(DesiredCapabilities.firefox());
                if (Browser.isDriverManager()) {
                    FirefoxDriverManager.getInstance().setup();
                    return new FirefoxDriver(getFirefoxOptionsHeadless());
                }
                return getFirefoxDriver();
            default:
                return null;
        }
    }

    /**
     * Gets grid remote driver.
     *
     * @param capabilities the capabilities
     *
     * @return the grid remote driver
     */
    private static RemoteWebDriver getGridRemoteDriver(DesiredCapabilities capabilities) {
        logger.info("Set selenium grid driver");
        try {
            return new RemoteWebDriver(new URL(Browser.getGridUrl()), capabilities);
        } catch (MalformedURLException exception) {
            logger.debug("Grid Remote Driver Initialization is fall", exception);
        }
        return null;
    }

    /**
     * Gets firefox driver.
     *
     * @return the firefox driver
     */
    private static RemoteWebDriver getFirefoxDriver() {
        URL myTestURL = null;
        File myFile = null;
        if (PLATFORM.contains("win")) {
            myTestURL = ClassLoader.getSystemResource(String.format("%s.exe", GECKODRIVER));
        } else if (PLATFORM.contains("linux") || PLATFORM.contains("mac")) {
            myTestURL = ClassLoader.getSystemResource(GECKODRIVER);
        } else {
            logger.fatal(String.format("Unsupported platform: %1$s for chrome browser %n", PLATFORM));
        }
        try {
            myFile = new File(myTestURL.toURI());
        } catch (URISyntaxException e1) {
            logger.debug(CLS_NAME + new Object(){}.getClass().getEnclosingMethod().getName(), e1);
        }
        System.setProperty(WEBDRIVER_GECKODRIVER, myFile.getAbsolutePath());
        return new FirefoxDriver(getFirefoxOptionsHeadless());
    }

    /**
     * Gets chrome driver.
     *
     * @return the chrome driver
     */
    private static RemoteWebDriver getChromeDriver() {
        URL myTestURL = null;
        File myFile = null;
        if (PLATFORM.contains("win")) {
            myTestURL = ClassLoader.getSystemResource(String.format("%s.exe", CHROMEDRIVER));
        } else if (PLATFORM.contains("linux") || PLATFORM.contains("mac")) {
            myTestURL = ClassLoader.getSystemResource(CHROMEDRIVER);
        } else {
            logger.fatal(String.format("Unsupported platform: %1$s for chrome browser %n", PLATFORM));
        }
        try {
            myFile = new File(myTestURL.toURI());
        } catch (URISyntaxException e1) {
            logger.debug(CLS_NAME + new Object(){}.getClass().getEnclosingMethod().getName(), e1);
        }
        System.setProperty(WEBDRIVER_CHROME, myFile.getAbsolutePath());
        RemoteWebDriver driver = new ChromeDriver(getChromeOptionsHeadless());
        driver.manage().window().maximize();
        return driver;
    }

    /**
     * Gets chrome options headless.
     *
     * @return the chrome options headless
     */
    private static ChromeOptions getChromeOptionsHeadless() {
        ChromeOptions options = new ChromeOptions();
        if (Browser.isBrowserHeadless()) {
            options.setHeadless(true);
        }
        return options;
    }

    /**
     * Gets firefox options headless.
     *
     * @return the firefox options headless
     */
    private static FirefoxOptions getFirefoxOptionsHeadless() {
        FirefoxBinary firefoxBinary = new FirefoxBinary();
        if (Browser.isBrowserHeadless()) {
            firefoxBinary.addCommandLineOptions("--headless");
        }
        FirefoxOptions firefoxOptions = new FirefoxOptions();
        firefoxOptions.setBinary(firefoxBinary);
        return firefoxOptions;
    }
}