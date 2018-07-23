package webdriver.common;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.testng.AssertJUnit.fail;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Synchronized;
import webdriver.Logger;

/**
 * The type Soft assert wrapper.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SoftAssertWrapper {
    private static SoftAssertWrapper softAssert = null;
    private static final Logger LOGGER = Logger.getInstance();

    /**
     * Gets instance.
     *
     * @return the instance
     */
    @Synchronized
    public static SoftAssertWrapper getInstance() {
        if (softAssert == null) {
            softAssert = new SoftAssertWrapper();
        }
        return softAssert;
    }


    /**
     * Assert equals.
     *
     * @param expected the expected
     * @param actual   the actual
     */
    public void assertEquals(@NonNull Object expected, @NonNull Object actual) {
        assertThat("Expected value: '" + expected.toString() + "', but was: '" + actual.toString() + "'", expected, is(equalTo(actual)));
    }

    /**
     * Fatal.
     *
     * @param message the message
     */
    public void fatal(final String message) {
        LOGGER.fatal(message);
        fail(message);
    }
}