package webdriver;

import demo.test.utils.FactoryInitParams;
import org.testng.annotations.DataProvider;

/**
 * The type Base test.
 */
public interface IDataProvider {

    /**
     * Gets data base location.
     *
     * @return the data base location
     */
    String getDataBaseLocation();

    /**
     * Get params object [ ].
     *
     * @return the object [ ]
     */
    @DataProvider(name = "initParams")
    default Object[] initParams() {
        String dataBaseLocation = this.getDataBaseLocation();
        Object[] dataProvider = getTestData(dataBaseLocation);
        if (dataProvider == null) {
            Logger.getInstance().error(String.format("Data not received from %s", dataBaseLocation));
        }
        return dataProvider;
    }

    /**
     * Get test data object [ ].
     *
     * @param dataBaseLocation the data base location
     *
     * @return the object [ ]
     */
    default Object[] getTestData(String dataBaseLocation) {
        return new FactoryInitParams().getTestData(dataBaseLocation);
    }
}