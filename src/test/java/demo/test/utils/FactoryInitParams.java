package demo.test.utils;

import lombok.NonNull;
import webdriver.common.ProviderData;

/**
 * The type Factory init parameters.
 */
public class FactoryInitParams implements ProviderData {
    private String dataBaseLocation;

    /**
     * Instantiates a new Factory init params.
     *
     * @param dataBaseLocation the data base location
     */
    public FactoryInitParams(String dataBaseLocation) {
        this.dataBaseLocation = dataBaseLocation;
    }

    /**
     * Gets test data.
     *
     * @return the test data
     */
    @Override
    public @NonNull Object[] getTestData() {
        if (dataBaseLocation.toUpperCase().endsWith(DataBaseType.XML.name())) {
            return new XMLParamsImpl().fetchTestData(dataBaseLocation);
        } else if (dataBaseLocation.toUpperCase().endsWith(DataBaseType.CSV.name())) {
            return new CSVParamsImpl().fetchTestData(dataBaseLocation);
        } else if (dataBaseLocation.toUpperCase().equals(DataBaseType.DB.name())) {
            return new DBParamsImpl().fetchTestData(dataBaseLocation);
        }
        return null;
    }

    /**
     * The enum Data base type.
     */
    public enum DataBaseType {
        XML,
        CSV,
        DB
    }
}