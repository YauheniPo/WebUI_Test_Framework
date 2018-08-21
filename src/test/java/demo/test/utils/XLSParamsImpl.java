package demo.test.utils;

import webdriver.utils.XLSParse;

/**
 * The type XLS parameters.
 */
public class XLSParamsImpl extends InitParams {

    /**
     * Fetch test data object [ ].
     *
     * @param dataBaseLocation the data baselocation
     *
     * @return the object [ ]
     */
    @Override
    public Object[] fetchTestData(String dataBaseLocation) {
        return getTestDataMails(new XLSParse(dataBaseLocation).getTableArray());
    }
}