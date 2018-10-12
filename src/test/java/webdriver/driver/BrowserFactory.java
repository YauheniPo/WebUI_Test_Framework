package webdriver.driver;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.SneakyThrows;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import webdriver.Logger;
import webdriver.driver.Browser.Browsers;

import javax.naming.NamingException;
import java.net.MalformedURLException;
import java.net.URI;

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
        switch (type) {
            case CHROME:
                Configuration.browser = WebDriverRunner.CHROME;
                break;
            case FIREFOX:
                Configuration.browser = WebDriverRunner.FIREFOX;
                break;
            default:
                LOGGER.error(String.format("WebDriver %s not created", type.name()));
                throw new NamingException("browser name wrong" + ":\nchrome\nfirefox");
        }
        if(Browser.isGrid()) {
            RemoteWebDriver gridDriver = getGridRemoteDriver(type);
            WebDriverRunner.setWebDriver(gridDriver);
        }
    }

    /**
     * Gets grid remote driver.
     *
     * @param browser the browser type
     *
     * @return the grid remote driver
     */
    @SneakyThrows(MalformedURLException.class)
    private static RemoteWebDriver getGridRemoteDriver(Browsers browser) {
        LOGGER.info("Set selenium grid driver");
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        desiredCapabilities.setBrowserName(browser.getValue());
        desiredCapabilities.setCapability("enableVNC", true);
        desiredCapabilities.setCapability("enableVideo", false);
        desiredCapabilities.setCapability(CapabilityType.TAKES_SCREENSHOT, true);
        return new RemoteWebDriver(URI.create(Browser.GRID_URL).toURL(), desiredCapabilities);
    }
}