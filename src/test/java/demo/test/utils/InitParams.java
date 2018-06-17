package demo.test.utils;

import demo.test.Models.TestDataMails;
import webdriver.Logger;

import java.util.List;

/**
 * The type Init parameters.
 */
public abstract class InitParams {
    /**
     * The Logger.
     */
    Logger LOGGER = Logger.getInstance();

    /**
     * Fetch test data init parameters.
     *
     * @param dataBaselocation the data baselocation
     *
     * @return the init params
     */
    public abstract Object[] fetchTestData(String dataBaselocation);

    Object[] getTestDataMails(List<String> testData) {
        Object[] dataProvider = new Object[testData.size() / 4];
        for (int i = 0, j = 0, l = testData.size(); i < l; i += 4, ++j) {
            dataProvider[j] = new TestDataMails(testData.get(i), testData.get(i + 1), testData.get(i + 2), testData.get(i + 3));
        }
        return dataProvider;
    }
}