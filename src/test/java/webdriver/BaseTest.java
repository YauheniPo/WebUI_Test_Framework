package webdriver;

import demo.test.utils.FactoryInitParams;
import demo.test.utils.InitParams;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * The type Base test.
 */
public abstract class BaseTest extends BaseEntity {
    /**
     * Run test.
     *
     * @param senderMailLogin       the sender mail login
     * @param senderMailPassword    the sender mail password
     * @param recipientMailLogin    the recipient mail login
     * @param recipientMailPassword the recipient mail password
     */
    public abstract void runTest(String senderMailLogin, String senderMailPassword,
                                 String recipientMailLogin, String recipientMailPassword);

    /**
     * Gets report data.
     *
     * @return the report data
     */
    public abstract String getReportData();

    /**
     * Get params object [ ] [ ].
     *
     * @return the object [ ] [ ]
     */
    @DataProvider(name = "initParams")
    public Object[][] getParams() {
        String dataBaseLocation = "dataBaseLocation";
        dataBaseLocation = Browser.getPropsStage().getProperty(dataBaseLocation);
        InitParams testData = new FactoryInitParams().getTestData(dataBaseLocation);
        if (testData == null) {
            logger.error(String.format("Data not received from %s", dataBaseLocation));
        }
        return new Object[][]{{testData.getSenderMailLogin(), testData.getSenderMailPassword(),
                testData.getRecipientMailLogin(), testData.getRecipientMailPassword()}};
    }

    /**
     * Unit test.
     *
     * @param senderMailLogin       the sender mail login
     * @param senderMailPassword    the sender mail password
     * @param recipientMailLogin    the recipient mail login
     * @param recipientMailPassword the recipient mail password
     */
    @Test(dataProvider = "initParams")
    public void xTest(String senderMailLogin, String senderMailPassword,
                      String recipientMailLogin, String recipientMailPassword) {
        Class<? extends BaseTest> currentClass = this.getClass();
        try {
            logger.logTestName(currentClass.getName());
            runTest(senderMailLogin, senderMailPassword, recipientMailLogin, recipientMailPassword);
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