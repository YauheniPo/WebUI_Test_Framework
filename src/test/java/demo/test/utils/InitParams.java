package demo.test.utils;

import demo.test.models.TestDataMails;
import webdriver.Logger;

import java.util.List;

/**
 * The type Init parameters.
 */
abstract class InitParams {
    final Logger logger = Logger.getInstance();

    /**
     * Fetch test data init parameters.
     *
     * @param dataBaseLocation the data baselocation
     *
     * @return the init params
     */
    public abstract Object[] fetchTestData(String dataBaseLocation);

    /**
     * Get test data mails object [ ].
     *
     * @param testData the test data
     *
     * @return the object [ ]
     */
    Object[] getTestDataMails(List<String> testData) {
        Object[] dataProvider = new Object[testData.size() / 4];
        for (int i = 0, j = 0, l = testData.size(); i + 4 <= l; i += 4, ++j) {
            dataProvider[j] = new TestDataMails(testData.get(i), testData.get(i + 1), testData.get(i + 2), testData.get(i + 3));
        }
        return dataProvider;
    }
}