package webdriver;

import org.springframework.beans.factory.annotation.Value;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * The type Base test.
 */
public abstract class BaseTestDataProvider extends BaseEntity {
    @Value("${dataBaseLocation}")
    private String dataBaseLocation = "accounts.xml";
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
     * Get test data object [ ].
     *
     * @param dataBaseLocation the data base location
     * @return the object [ ]
     */
    protected abstract Object[] getTestData(String dataBaseLocation);

    /**
     * Test method
     *
     * @param testData emails test data
     */
    @Test(dataProvider = "initParams")
    public void xTest(Object testData) {
        Class<? extends BaseTestDataProvider> currentClass = this.getClass();
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

    @DataProvider(name = "initParams")
    public Object[] getParams() {
        Object[] dataProvider = getTestData(dataBaseLocation);
        if (dataProvider == null) {
            LOGGER.error(String.format("Data not received from %s", dataBaseLocation));
        }
        return dataProvider;
    }
}