package webdriver;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.testng.AssertJUnit.fail;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Synchronized;

import java.util.HashMap;
import java.util.Map;

/**
 * The type Soft assert wrapper.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AssertWrapper {
    private static AssertWrapper assertWrapper = null;
    private static final Logger LOGGER = Logger.getInstance();
    private static volatile Map<AssertionError, String> results = new HashMap<>();

    /**
     * Gets instance.
     *
     * @return the instance
     */
    @Synchronized
    public static AssertWrapper getInstance() {
        if (assertWrapper == null) {
            assertWrapper = new AssertWrapper();
        }
        return assertWrapper;
    }

    /**
     * Assert equals.
     *
     * @param expected the expected
     * @param actual   the actual
     */
    public void assertEquals(@NonNull final Object expected, @NonNull final Object actual) {
        assertThat("Expected value: '" + expected.toString() + "', but was: '" + actual.toString() + "'", expected, is(equalTo(actual)));
    }

    /**
     * Assert true.
     *
     * @param assertion the assertion
     */
    public void assertTrue(boolean assertion) {
        assertThat("Expected value doesn't TRUE", assertion);
    }

    /**
     * Fatal.
     *
     * @param message the message
     */
    public void fatal(final String message) {
        LOGGER.error(message);
        fail(message);
    }

    /**
     * Soft assert equals.
     *
     * @param actual   the actual
     * @param expected the expected
     * @param message  the message
     */
    public void softAssertEquals(Object actual, Object expected, String message) {
        try {
            assertEquals(expected, actual);
        } catch (AssertionError error) {
            LOGGER.error(message);
            results.put(error, message);
        }
    }


    /**
     * Soft assert true.
     *
     * @param condition the condition
     * @param message   the message
     */
    public void softAssertTrue(boolean condition, String message) {
        try {
            assertTrue(condition);
        } catch (AssertionError error) {
            LOGGER.error(message);
            results.put(error, message);
        }
    }

    /**
     * Soft assert all.
     */
    public void softAssertAll() {
        if (!results.isEmpty()) {
            results.forEach((error, message) -> LOGGER.printStackTrace(error));
            results.clear();
            fatal("Some of assertions were completed with errors\r\n");
        }
    }
}