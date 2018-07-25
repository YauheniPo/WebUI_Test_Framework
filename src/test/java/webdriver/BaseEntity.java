package webdriver;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.openqa.selenium.By;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import webdriver.asserts.AssertWrapper;
import webdriver.elements.Label;

/**
 * The type Base entity.
 */
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class BaseEntity {
    protected By titleLocator;
    protected String title;
    protected static final Logger LOGGER = Logger.getInstance();
    protected static final AssertWrapper ASSERT_WRAPPER = AssertWrapper.getInstance();

    /**
     * Instantiates a new Base entity.
     *
     * @param locator   the locator
     * @param formTitle the form title
     */
    protected BaseEntity(@NonNull final By locator, @NonNull final String formTitle) {
        this.titleLocator = locator;
        this.title = formTitle;
    }

    /**
     * Gets browser.
     *
     * @return the browser
     */
    private Browser getBrowser() {
        return Browser.getInstance();
    }

    /**
     * Before.
     */
    @BeforeTest
    public void before() {
        Browser browser = getBrowser();
        browser.windowMaximise();
        browser.navigate(Browser.getBrowserURL());
    }

    /**
     * After.
     */
    @AfterTest
    public void after() {
        getBrowser().exit();
    }

    /**
     * Debug.
     *
     * @param message the message
     */
    protected final void debug(final String message) {
        LOGGER.debug(String.format("[%1$s] %2$s", this.getClass().getSimpleName(), (message)));
    }

    /**
     * Info.
     *
     * @param message the message
     */
    protected void info(final String message) {
        LOGGER.info(message);
    }

    /**
     * Error.
     *
     * @param message the message
     */
    protected void error(final String message) {
        LOGGER.error(message);
    }

    /**
     * Check form is displayed.
     */
    void checkIsDisplayed() {
        try {
            new Label(this.titleLocator, title).waitForIsElementPresent();
        } catch (Exception e) {
            debug(e.getMessage());
            ASSERT_WRAPPER.fatal(String.format("Locator form %1$s doesn't appear", this.title));
        }
        info(String.format("Locator of %1$s Form appears", this.title));
    }
}