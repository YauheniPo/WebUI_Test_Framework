package webdriver;

import demo.test.utils.FactoryInitParams;
import org.testng.annotations.DataProvider;

/**
 * The type Base test.
 */
public abstract class BaseTestDataProvider extends BaseTest {
    protected String dataBaseLocation;

    /**
     * Get params object [ ].
     *
     * @return the object [ ]
     */
    @DataProvider(name = "initParams")
    protected Object[] initParams() {
        Object[] dataProvider = getTestData(this.dataBaseLocation);
        if (dataProvider == null) {
            LOGGER.error(String.format("Data not received from %s", this.dataBaseLocation));
        }
        return dataProvider;
    }

    private Object[] getTestData(String dataBaseLocation) {
        return new FactoryInitParams().getTestData(dataBaseLocation);
    }
}