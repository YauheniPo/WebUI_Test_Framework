package webdriver;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.testng.AssertJUnit.fail;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Synchronized;

/**
 * The type Soft assert wrapper.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AssertWrapper {
    private static AssertWrapper assertWrapper = null;
    private static final Logger LOGGER = Logger.getInstance();

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
        LOGGER.fatal(message);
        fail(message);
    }
}