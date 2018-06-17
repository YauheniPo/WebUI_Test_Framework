package demo.test.utils;

import webdriver.utils.CSVParse;

/**
 * The type Csv parameters.
 */
public class CSVParamsImpl extends InitParams {

    @Override
    public Object[] fetchTestData(String dataBaselocation) {
        return getTestDataMails(new CSVParse(dataBaselocation).fetchCSVData());
    }
}