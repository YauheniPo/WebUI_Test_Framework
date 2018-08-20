package webdriver;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.openqa.selenium.By;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import webdriver.asserts.AssertWrapper;

/**
 * The type Base entity.
 */
@ContextConfiguration(classes = {ImportResourceValueProperties.class},
        loader = AnnotationConfigContextLoader.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BaseEntity extends AbstractTestNGSpringContextTests {
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
}