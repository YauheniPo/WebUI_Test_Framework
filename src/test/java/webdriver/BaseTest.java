package webdriver;

import org.testng.annotations.Test;
import webdriver.common.ProviderData;

/**
 * The type Base test.
 */
public abstract class BaseTest extends BaseEntity {

    /**
     * Run Test
     *
     * @param testData emails test data
     */
    protected abstract void runTest(Object testData);

    /**
     * Gets report data.
     *
     * @return the report data
     */
    protected abstract String getReportData();

    /**
     * Test method
     *
     * @param testData emails test data
     */
    @Test(dataProvider = "initParams", dataProviderClass = ProviderData.class)
    public void xTest(Object testData) {
        Class<? extends BaseTest> currentClass = this.getClass();
        try {
            LOGGER.logTestName(currentClass.getName());
            runTest(testData);
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