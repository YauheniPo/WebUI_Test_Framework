package demo.test.utils;

/**
 * The type Factory init parameters.
 */
public class FactoryInitParams {

    /**
     * Gets test data.
     *
     * @param dataBaseLocation the data base location
     *
     * @return the test data
     */
    public InitParams getTestData(String dataBaseLocation) {
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
        /**
         * Xml data base type.
         */
        XML,
        /**
         * Csv data base type.
         */
        CSV,
        /**
         * Db data base type.
         */
        DB
    }
}