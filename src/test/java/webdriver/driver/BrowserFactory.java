package webdriver.driver;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.SneakyThrows;
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
     * @throws NamingException the naming exception
     */
    public static void setUp(@NonNull final Browsers type) throws NamingException {
        for (Browsers t : Browsers.values()) {
            if (t == type) {
                setWebDriver(t);
                return;
            }
        }
        throw new NamingException("browser name wrong" + ":\nchrome\nfirefox");
    }

    /**
     * Set web driver.
     *
     * @param type the type
     *
     * @throws NamingException the naming exception
     */
    private static void setWebDriver(@NonNull final Browsers type) throws NamingException {
        Configuration.headless = Browser.IS_HEADLESS;
        boolean isGrid = Browser.isGrid();
        switch (type) {
            case CHROME:
                Configuration.browser = WebDriverRunner.CHROME;
                if (isGrid) {
                    RemoteWebDriver gridDriver = getGridRemoteDriver(DesiredCapabilities.chrome());
                    WebDriverRunner.setWebDriver(gridDriver);
                }
                break;
            case FIREFOX:
                Configuration.browser = WebDriverRunner.FIREFOX;
                if (isGrid) {
                    RemoteWebDriver gridDriver = getGridRemoteDriver(DesiredCapabilities.firefox());
                    WebDriverRunner.setWebDriver(gridDriver);
                }
                break;
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
}