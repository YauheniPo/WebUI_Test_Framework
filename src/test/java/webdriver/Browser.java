package webdriver;

import org.openqa.selenium.remote.RemoteWebDriver;

import javax.naming.NamingException;
import java.util.concurrent.TimeUnit;

import static webdriver.ConstantsFrm.PROPERTIES_SELENIUM;
import static webdriver.ConstantsFrm.PROPERTIES_STAGE;

/**
 * The type Browser.
 */
public final class Browser {
	private static final String BROWSER_BY_DEFAULT = Browsers.FIREFOX.value;
	private static final String BROWSER_PROP = "browser";
	private static final String STAGE = "stage";
	private static Browser instance;
	private static RemoteWebDriver driver;
	private static final PropertiesResourceManager props = new PropertiesResourceManager(PROPERTIES_SELENIUM);
	private static PropertiesResourceManager propStage;
	private static String browserURL;
	private static boolean isDriverManager;
	private static boolean isHeadless;
	private static String timeoutForCondition;
	private static final Browsers currentBrowser
			= Browsers.valueOf(System.getProperty(BROWSER_PROP, props.getProperty(BROWSER_PROP, BROWSER_BY_DEFAULT).toUpperCase()));
	private static final long IMPLICITY_WAIT = Long.valueOf(props.getProperty("implicityWait", String.valueOf(10)));
	private static final String DEFAULT_CONDITION_TIMEOUT = "defaultConditionTimeout";
	private static final String URL_LOGIN_PAGE = "urlLoginPage";
	private static final String DRIVER_MANAGER = "driverManager";
	private static final String BROWSER_HEADLESS = "browserHeadless";
	private static final Logger logger = Logger.getInstance();

	private Browser() {
		logger.info(String.format("browser %s is ready", currentBrowser.name()));
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
	 * Gets browser name.
	 *
	 * @return the browser name
	 */
	public static String getBrowserName() {
		return currentBrowser.name();
	}

	/**
	 * Gets props stage.
	 *
	 * @return the props stage
	 */
	static PropertiesResourceManager getPropsStage() {
		return propStage;
	}

	/**
	 * Gets driver.
	 *
	 * @return the driver
	 */
	public static RemoteWebDriver getDriver() {
		if(driver == null){
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
	 * Refresh.
	 */
	public void refresh() {
		getDriver().navigate().refresh();
		logger.info("Page was refreshed.");
	}

	/**
	 * The enum Browsers.
	 */
	public enum Browsers {
		/**
		 * Firefox browsers.
		 */
		FIREFOX("firefox"),
		/**
		 * Chrome browsers.
		 */
		CHROME("chrome");

		private String value;

		Browsers(final String values) {
			value = values;
		}
	}

	private static void initProperties() {
		isDriverManager = Boolean.valueOf(props.getProperty(DRIVER_MANAGER, "false"));
		isHeadless = Boolean.valueOf(props.getProperty(BROWSER_HEADLESS, "false"));
		timeoutForCondition = props.getProperty(DEFAULT_CONDITION_TIMEOUT);
		propStage = new PropertiesResourceManager(PROPERTIES_STAGE);
		String choosenStage = propStage.getProperty(STAGE);
		browserURL = String.format(propStage.getProperty(URL_LOGIN_PAGE), choosenStage);
		driver = getNewDriver();
	}

	private static RemoteWebDriver getNewDriver() {
		try {
			RemoteWebDriver driver = BrowserFactory.setUp(currentBrowser.toString());
			driver.manage().timeouts().implicitlyWait(IMPLICITY_WAIT, TimeUnit.SECONDS);
			logger.info("browser constructed");
			return driver;
		} catch (NamingException e) {
			logger.debug("Browser: getting New Driver", e);
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
	 * Window maximise.
	 */
	void windowMaximise() {
		try {
			getDriver().executeScript("if (window.screen) {window.moveTo(0, 0);window.resizeTo(window.screen.availWidth,window.screen.availHeight);};");
			getDriver().manage().window().maximize();
		} catch (Exception e) {
			logger.debug(e);
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
			logger.info("browser driver quit");
		} catch (Exception e) {
			logger.debug(e.getMessage());
		}
	}
}