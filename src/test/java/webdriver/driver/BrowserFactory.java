package webdriver.driver;

import io.github.bonigarcia.wdm.ChromeDriverManager;
import io.github.bonigarcia.wdm.FirefoxDriverManager;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.SneakyThrows;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import webdriver.Logger;
import webdriver.driver.Browser.Browsers;

import javax.naming.NamingException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * The type Browser factory.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
final public class BrowserFactory {
    private static final Logger LOGGER = Logger.getInstance();

    /**
     * Sets up.
     *
     * @param type the type
     *
     * @return the up
     *
     * @throws NamingException the naming exception
     */
    public static RemoteWebDriver setUp(@NonNull final Browsers type) throws NamingException {
        for (Browsers t : Browsers.values()) {
            if (t == type) {
                return getWebDriver(t);
            }
        }
        throw new NamingException("browser name wrong" + ":\nchrome\nfirefox");
    }

    /**
     * Gets web driver.
     *
     * @param type the type
     *
     * @return the web driver
     *
     * @throws NamingException the naming exception
     */
    private static RemoteWebDriver getWebDriver(@NonNull final Browsers type) throws NamingException {
        boolean isGrid = Browser.isGrid();
        switch (type) {
            case CHROME:
                if (isGrid)
                    return getGridRemoteDriver(DesiredCapabilities.chrome());
                ChromeDriverManager.getInstance().setup();
                return new ChromeDriver(getChromeOptionsHeadless());
            case FIREFOX:
                if (isGrid)
                    return getGridRemoteDriver(DesiredCapabilities.firefox());
                FirefoxDriverManager.getInstance().setup();
                return new FirefoxDriver(getFirefoxOptionsHeadless());
            default:
                LOGGER.error(String.format("WebDriver %s not created", type.name()));
                throw new NamingException("browser name wrong" + ":\nchrome\nfirefox");
        }
    }

    /**
     * Gets grid remote driver.
     *
     * @param capabilities the capabilities
     *
     * @return the grid remote driver
     */
    @SneakyThrows(MalformedURLException.class)
    private static RemoteWebDriver getGridRemoteDriver(DesiredCapabilities capabilities) {
        LOGGER.info("Set selenium grid driver");
        return new RemoteWebDriver(new URL(Browser.GRID_URL), capabilities);
    }

    /**
     * Gets chrome options headless.
     *
     * @return the chrome options headless
     */
    private static ChromeOptions getChromeOptionsHeadless() {
        ChromeOptions options = new ChromeOptions();
        if (Browser.IS_HEADLESS) {
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
        if (Browser.IS_HEADLESS) {
            firefoxBinary.addCommandLineOptions("--headless");
        }
        FirefoxOptions firefoxOptions = new FirefoxOptions();
        firefoxOptions.setBinary(firefoxBinary);
        return firefoxOptions;
    }
}