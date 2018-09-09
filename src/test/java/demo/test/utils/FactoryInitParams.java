package demo.test.utils;

import lombok.NonNull;
import webdriver.common.ProviderData;

/**
 * The type Factory init parameters.
 */
public class FactoryInitParams implements ProviderData {

    /**
     * Gets test data.
     *
     * @return the test data
     */
    @Override
    public @NonNull Object[] getTestData(String dataBaseLocation) {
        if (dataBaseLocation.toUpperCase().endsWith(DataBaseType.XML.name())) {
            return new XMLParamsImpl().fetchTestData(dataBaseLocation);
        } else if (dataBaseLocation.toUpperCase().endsWith(DataBaseType.CSV.name())) {
            return new CSVParamsImpl().fetchTestData(dataBaseLocation);
        } else if (dataBaseLocation.toUpperCase().endsWith(DataBaseType.DB.name())) {
            return new DBParamsImpl().fetchTestData(dataBaseLocation);
        } else if (dataBaseLocation.toUpperCase().endsWith(DataBaseType.XLS.name())) {
            return new XLSParamsImpl().fetchTestData(dataBaseLocation);
        }
        return null;
    }
}