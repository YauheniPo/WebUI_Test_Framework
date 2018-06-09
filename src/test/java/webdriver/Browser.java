package webdriver;

import org.openqa.selenium.remote.RemoteWebDriver;

import javax.naming.NamingException;
import java.util.concurrent.TimeUnit;

import static webdriver.ConstantsFrm.PROPERTIES_SELENIUM;
import static webdriver.ConstantsFrm.PROPERTIES_STAGE;

public final class Browser {
	// имя файла с настройками Selenium
	private static final String BROWSER_BY_DEFAULT = Browsers.FIREFOX.value;
	private static final String BROWSER_PROP = "browser";
	private static final String STAGE = "stage";
	// browsers
	private static Browser instance;
	private static RemoteWebDriver driver;
	private static final PropertiesResourceManager props = new PropertiesResourceManager(PROPERTIES_SELENIUM);
	// поля класса
	private static String browserURL;
	private static String timeoutForCondition;
	private static final Browsers currentBrowser
			= Browsers.valueOf(System.getProperty(BROWSER_PROP, props.getProperty(BROWSER_PROP, BROWSER_BY_DEFAULT).toUpperCase()));
	private static final long IMPLICITY_WAIT = Long.valueOf(props.getProperty("implicityWait", String.valueOf(10)));
	private static final String DEFAULT_CONDITION_TIMEOUT = "defaultConditionTimeout";
	private static final String URL_LOGIN_PAGE = "urlLoginPage";
	private static final Logger logger = Logger.getInstance();

	private Browser() {
		Logger.getInstance().info(String.format("browser %s is ready", currentBrowser.name()));
	}

	synchronized static Browser getInstance() {
		if (instance == null) {
			initProperties();
			instance = new Browser();
		}
		return instance;
	}

	public static String getBrowserName() {
		return currentBrowser.name();
	}

	public static RemoteWebDriver getDriver() {
		if(driver == null){
			driver = getNewDriver();
		}
		return driver;
	}

	public static String getTimeoutForCondition() {
		return timeoutForCondition;
	}

	public static void openStartPage() {
		getDriver().navigate().to(browserURL);
	}

	public void refresh() {
		getDriver().navigate().refresh();
		Logger.getInstance().info("Page was refreshed.");
	}

	public enum Browsers {
		FIREFOX("firefox"),
		CHROME("chrome");

		private String value;

		Browsers(final String values) {
			value = values;
		}
	}

	private static void initProperties() {
		timeoutForCondition = props.getProperty(DEFAULT_CONDITION_TIMEOUT);
		PropertiesResourceManager propUrls = new PropertiesResourceManager(PROPERTIES_STAGE);
		String choosenStage = propUrls.getProperty(STAGE);
		browserURL = String.format(propUrls.getProperty(URL_LOGIN_PAGE), choosenStage);
		driver = getNewDriver();
	}

	private static RemoteWebDriver getNewDriver() {
		try {
			RemoteWebDriver driver = BrowserFactory.setUp(currentBrowser.toString());
			driver.manage().timeouts().implicitlyWait(IMPLICITY_WAIT, TimeUnit.SECONDS);
			Logger.getInstance().info("loc.browser.constructed");
			return driver;
		} catch (NamingException e) {
			logger.debug("Browser.getNewDriver", e);
		}
		return null;
	}

	static String getStartBrowserURL() {
		return browserURL;
	}

	void windowMaximise() {
		try {
			getDriver().executeScript("if (window.screen) {window.moveTo(0, 0);window.resizeTo(window.screen.availWidth,window.screen.availHeight);};");
			getDriver().manage().window().maximize();
		} catch (Exception e) {
			logger.debug(e);
		}
	}

	void navigate(final String url) {
		getDriver().navigate().to(url);
	}

	void exit() {
		try {
			getDriver().quit();
			Logger.getInstance().info("browser driver quit");
		} catch (Exception e) {
			logger.debug(e.getMessage());
		}
	}
}