package webdriver;

import io.github.bonigarcia.wdm.ChromeDriverManager;
import io.github.bonigarcia.wdm.FirefoxDriverManager;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;
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

import static webdriver.Constants.*;

/**
 * The type Browser factory.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
final public class BrowserFactory {
    private static final String PLATFORM = System.getProperty("os.name").toLowerCase();
    private static final Logger logger = Logger.getInstance();
    private static final String CLS_NAME = BrowserFactory.class.getName();

    /**
     * Sets up.
     *
     * @param type the type
     *
     * @return the up
     */
    public static RemoteWebDriver setUp(@NonNull final Browsers type) {
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
    public static RemoteWebDriver setUp(@NonNull final String type) throws NamingException {
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
    private static RemoteWebDriver getWebDriver(@NonNull final Browsers type) {
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
        setDriverProperty(GECKODRIVER, WEBDRIVER_GECKODRIVER);
        return new FirefoxDriver(getFirefoxOptionsHeadless());
    }

    /**
     * Gets chrome driver.
     *
     * @return the chrome driver
     */
    private static RemoteWebDriver getChromeDriver() {
        setDriverProperty(CHROMEDRIVER, WEBDRIVER_CHROME);
        return new ChromeDriver(getChromeOptionsHeadless());
    }

    /**
     * Set Driver Property
     *
     * @param browsDriver the browser driver name
     * @param webBrowsDriver the browser webdriver name
     */
    private static void setDriverProperty(@NonNull final String browsDriver, @NonNull final String webBrowsDriver) {
        URL myTestURL = null;
        File myFile = null;
        if (PLATFORM.contains("win")) {
            myTestURL = ClassLoader.getSystemResource(String.format("%s.exe", browsDriver));
        } else if (PLATFORM.contains("linux") || PLATFORM.contains("mac")) {
            myTestURL = ClassLoader.getSystemResource(browsDriver);
        } else {
            logger.fatal(String.format("Unsupported platform: %s for browser for %s", browsDriver, PLATFORM));
        }
        try {
            if (myTestURL != null) {
                myFile = new File(myTestURL.toURI());
            }
        } catch (URISyntaxException e1) {
            logger.debug(CLS_NAME + BrowserFactory.class.getEnclosingMethod().getName(), e1);
        }
        if (myFile != null) {
            System.setProperty(webBrowsDriver, myFile.getAbsolutePath());
        }
    }

    /**
     * Gets chrome options headless.
     *
     * @return the chrome options headless
     */
    private static ChromeOptions getChromeOptionsHeadless() {
        ChromeOptions options = new ChromeOptions();
        if (Browser.isHeadless()) {
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
        if (Browser.isHeadless()) {
            firefoxBinary.addCommandLineOptions("--headless");
        }
        FirefoxOptions firefoxOptions = new FirefoxOptions();
        firefoxOptions.setBinary(firefoxBinary);
        return firefoxOptions;
    }
}