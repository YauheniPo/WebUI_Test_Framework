package webdriver;

import org.testng.annotations.Test;

/**
 * The type Base test.
 */
public abstract class BaseTest extends BaseEntity {

    /**
     * Run Test
     */
    protected abstract void runTest();

    /**
     * Gets report data.
     *
     * @return the report data
     */
    protected abstract String getReportData();

    /**
     * Test method
     */
    @Test
    public void xTest() {
        Class<? extends BaseTest> currentClass = this.getClass();
        try {
            LOGGER.logTestName(currentClass.getName());
            runTest();
            LOGGER.logTestEnd(getReportData());
        } catch (Exception ex) {
            LOGGER.debug(ex.getMessage());
            throw ex;
        } catch (AssertionError as) {
            LOGGER.logTestEnd(getReportData(), as.getMessage());
            throw as;
        }
    }
}