package webdriver;

import demo.test.Models.TestDataMails;
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
    public abstract void runTest(Object testData);

    /**
     * Gets report data.
     *
     * @return the report data
     */
    public abstract String getReportData();

    /**
     * Get params object [ ]
     *
     * @return the object [ ]
     */
    @DataProvider(name = "initParams")
    public Object[] getParams() {
        String dataBaseLocation = "dataBaseLocation";
        dataBaseLocation = Browser.getPropsStage().getProperty(dataBaseLocation);
        Object[] dataProvider = new FactoryInitParams().getTestData(dataBaseLocation);
        if (dataProvider == null) {
            logger.error(String.format("Data not received from %s", dataBaseLocation));
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
            logger.logTestName(currentClass.getName());
            runTest(testData);
            logger.logTestEnd(getReportData());
        } catch (Exception ex) {
            logger.debug(ex.getMessage());
            throw ex;
        } catch (AssertionError as) {
            logger.logTestEnd(getReportData(), as.getMessage());
            makeScreen(currentClass);
            throw as;
        }
    }
}