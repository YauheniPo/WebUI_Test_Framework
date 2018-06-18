package demo.test.utils;

import webdriver.utils.CSVParse;

/**
 * The type Csv parameters.
 */
public class CSVParamsImpl extends InitParams {

    /**
     * Fetch test data object [ ].
     *
     * @param dataBaselocation the data baselocation
     *
     * @return the object [ ]
     */
    @Override
    public Object[] fetchTestData(String dataBaselocation) {
        return getTestDataMails(new CSVParse(dataBaselocation).fetchCSVData());
    }
}