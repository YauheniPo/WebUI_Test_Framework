package webdriver;

import org.testng.annotations.AfterMethod;

/**
 * The type Base test.
 */
public class BaseTest extends BaseEntity {

    @AfterMethod
    public void afterMethod() {
        ASSERT_WRAPPER.softAssertAll();
    }
}