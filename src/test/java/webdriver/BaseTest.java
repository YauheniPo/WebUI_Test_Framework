package webdriver;

import demo.test.utils.FactoryInitParams;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

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
     * Get params object [ ]
     *
     * @return the object [ ]
     */
    @DataProvider(name = "initParams")
    public Object[] getParams() {
        String dataBaseLocation = Browser.getPropsStage().getProperty("dataBaseLocation");
        Object[] dataProvider = new FactoryInitParams().getTestData(dataBaseLocation);
        if (dataProvider == null) {
            LOGGER.error(String.format("Data not received from %s", dataBaseLocation));
        }
        return dataProvider;
    }

    /**
     * Test method
     *
     * @param testData emails test data
     */
    @Test(dataProvider = "initParams")
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
            makeScreen(currentClass);
            throw as;
        }
    }
}