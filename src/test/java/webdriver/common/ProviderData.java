package webdriver.common;

import demo.test.utils.FactoryInitParams;
import lombok.NoArgsConstructor;
import org.testng.annotations.DataProvider;
import webdriver.Browser;
import webdriver.Logger;

/**
 * The type Provider data.
 */
@NoArgsConstructor
public class ProviderData {
    private String dataBaseLocation = null;
    private static final Logger LOGGER = Logger.getInstance();

    /**
     * Instantiates a new Provider data.
     *
     * @param dataBaseLocation the data base location
     */
    public ProviderData(String dataBaseLocation) {
        this.dataBaseLocation = dataBaseLocation;
    }

    /**
     * Get params object [ ]
     *
     * @return the object [ ]
     */
    @DataProvider(name = "initParams")
    public Object[] getParams() {
        String dataBaseLocation = this.dataBaseLocation == null ? Browser.getPropStage().getProperty("dataBaseLocation") : this.dataBaseLocation;
        Object[] dataProvider = new FactoryInitParams().getTestData(dataBaseLocation);
        if (dataProvider == null) {
            LOGGER.error(String.format("Data not received from %s", dataBaseLocation));
        }
        return dataProvider;
    }
}