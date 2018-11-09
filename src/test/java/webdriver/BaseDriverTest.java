package webdriver;

import org.testng.annotations.AfterMethod;

/**
 * The type Base test.
 */
public class BaseDriverTest extends DriverEntity {

    @AfterMethod
    public void afterMethod() {
        ASSERT_WRAPPER.softAssertAll();
    }
}