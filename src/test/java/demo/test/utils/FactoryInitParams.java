package demo.test.utils;

public class FactoryInitParams {

    public InitParams getTestData(String dataBaseLocation) {
        if (dataBaseLocation.toUpperCase().endsWith(DataBaseType.XML.name())) {
            return new XMLParamsImpl().fetchTestData(dataBaseLocation);
        } else if (dataBaseLocation.toUpperCase().endsWith(DataBaseType.CSV.name())) {
            return new CSVParamsImpl().fetchTestData(dataBaseLocation);
        } else if (!dataBaseLocation.split(":")[0].equals(dataBaseLocation)) {
            return new DBParamsImpl().fetchTestData(dataBaseLocation);
        }
        return null;
    }

    public enum DataBaseType {
        XML, CSV, DB
    }
}