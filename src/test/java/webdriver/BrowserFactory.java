package webdriver;

import io.github.bonigarcia.wdm.ChromeDriverManager;
import io.github.bonigarcia.wdm.FirefoxDriverManager;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import webdriver.Browser.Browsers;

import javax.naming.NamingException;
import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static webdriver.ConstantsFrm.CHROMEDRIVER;
import static webdriver.ConstantsFrm.WEBDRIVER_CHROME;

/**
 * The type Browser factory.
 */
final public class BrowserFactory {
    private static final String PLATFORM = System.getProperty("os.name").toLowerCase();
    private static final Logger logger = Logger.getInstance();
    private static final String CLS_NAME = BrowserFactory.class.getName();

    private BrowserFactory() {
        // do not instantiate BrowserFactory class
    }

    /**
     * Sets up.
     *
     * @param type the type
     * @return the up
     */
    public static RemoteWebDriver setUp(final Browsers type) {
        return getWebDriver(type);
    }

    /**
     * Sets up.
     *
     * @param type the type
     * @return the up
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

    private static RemoteWebDriver getWebDriver(final Browsers type) {
        DesiredCapabilities capabilitiesProxy = new DesiredCapabilities();
        switch (type) {
            case CHROME:
                if (Browser.isDriverManager()) {
                    ChromeDriverManager.getInstance().setup();
                    return new ChromeDriver(capabilitiesProxy);
                }
                return getChromeDriver();
            case FIREFOX:
                if (Browser.isDriverManager()) {
                    FirefoxDriverManager.getInstance().setup();
                    return new FirefoxDriver(capabilitiesProxy);
                }
                return getFirefoxDriver(capabilitiesProxy);
            default:
                return null;
        }
    }
    
    private static RemoteWebDriver getFirefoxDriver(DesiredCapabilities capabilities) {
        DesiredCapabilities caps =  (capabilities != null) ? capabilities : new DesiredCapabilities();
        FirefoxProfile ffProfile = new FirefoxProfile();
        ffProfile.setPreference("browser.helperApps.neverAsk.saveToDisk",
				"application/x-debian-package; application/octet-stream");
        ffProfile.setPreference("browser.download.manager.showWhenStarting", false);
        ffProfile.setPreference("pdfjs.disabled", true);
        ffProfile.setPreference("browser.helperApps.alwaysAsk.force", false);

        return new FirefoxDriver(new FirefoxBinary(), ffProfile, caps);
    }
    
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
        ChromeOptions options = new ChromeOptions();
		Map<String, Object> prefs = new HashMap<>();
		prefs.put("profile.default_content_settings.popups", "0");
		prefs.put("credentials_enable_service", false);
		prefs.put("profile.password_manager_enabled", false);
		prefs.put("safebrowsing.enabled", "true"); 
		options.setExperimentalOption("prefs", prefs);
		options.addArguments("--headless");
        DesiredCapabilities cp1 = DesiredCapabilities.chrome();
        cp1.setCapability("chrome.switches", Collections.singletonList("--disable-popup-blocking"));
        cp1.setCapability(ChromeOptions.CAPABILITY, options);
        try {
            myFile = new File(myTestURL.toURI());
        } catch (URISyntaxException e1) {
            logger.debug(CLS_NAME + new Object(){}.getClass().getEnclosingMethod().getName(), e1);
        }
        System.setProperty(WEBDRIVER_CHROME, myFile.getAbsolutePath());
        cp1.setCapability(ChromeOptions.CAPABILITY, options);
        RemoteWebDriver driver = new ChromeDriver(cp1);
        if (Browser.isBrowserHeadless()) {
            driver.manage().window().maximize();
        }
        return driver;
    }
}