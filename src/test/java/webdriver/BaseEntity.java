package webdriver;

import com.epam.reportportal.testng.ReportPortalTestNGListener;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.openqa.selenium.By;
import org.testng.IInvokedMethodListener;
import org.testng.IReporter;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;
import webdriver.driver.Browser;

/**
 * The type Base entity.
 */
@Listeners({ReportPortalTestNGListener.class})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BaseEntity implements IInvokedMethodListener, IReporter {
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
        Browser.navigate(Browser.BROWSER_URL);
        browser.windowMaximise();
    }
}