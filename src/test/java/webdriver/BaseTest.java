package webdriver;

import demo.test.utils.FactoryInitParams;
import demo.test.utils.InitParams;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public abstract class BaseTest extends BaseEntity {
    public abstract void runTest(String senderMailLogin, String senderMailPassword,
                                 String recipientMailLogin, String recipientMailPassword);
    public abstract String getReportData();

    @DataProvider(name = "initParams")
    public Object[][] getParams() {
        String dataBaseLocation = Browser.getPropsStage().getProperty("dataBaseLocation");
        InitParams testData = new FactoryInitParams().getTestData(dataBaseLocation);
        if (testData == null) {
            logger.error(String.format("Data not received from %s", dataBaseLocation));
        }
        return new Object[][]{{testData.getSenderMailLogin(), testData.getSenderMailPassword(),
                testData.getRecipientMailLogin(), testData.getRecipientMailPassword()}};
    }

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