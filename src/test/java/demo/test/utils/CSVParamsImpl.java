package demo.test.utils;

import webdriver.utils.CSVParse;

/**
 * The type Csv parameters.
 */
public class CSVParamsImpl extends InitParams {

    @Override
    public InitParams fetchTestData(String dataBaselocation) {
        setParams(new CSVParse(dataBaselocation).fetchCSVData());
        return this;
    }
}