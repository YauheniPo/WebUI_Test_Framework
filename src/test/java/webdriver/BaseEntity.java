package webdriver;

import com.epam.reportportal.testng.ReportPortalTestNGListener;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.SneakyThrows;
import org.openqa.selenium.By;
import org.testng.IInvokedMethodListener;
import org.testng.IReporter;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import webdriver.driver.Browser;

import java.awt.*;

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
     * @param browser   name of browser
     *
     * @return the browser
     */
    private Browser getBrowser(String browser) {
        return Browser.getInstance(browser);
    }

    /**
     * Take off cursor.
     */
    @SneakyThrows(AWTException.class)
    private void takeOffCursor() {
        Robot robot = new Robot();
        robot.mouseMove(0,0);
    }

    /**
     * Before.
     *
     * @param browser   name of browser
     */
    @Parameters({"browser"})
    @BeforeTest(alwaysRun = true)
    public void before(@Optional(value = "true") String browser) {
        Browser br = getBrowser(browser);
        Browser.navigate(Browser.BROWSER_URL);
        br.windowMaximise();
        takeOffCursor();
    }

    /**
     * Before.
     */
    protected void before() {
        before("true");
    }
}