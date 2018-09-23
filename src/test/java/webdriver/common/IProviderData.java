package webdriver.common;

import webdriver.Logger;

/**
 * The type Provider data.
 */
public interface IProviderData {
    Logger LOGGER = Logger.getInstance();

    /**
     * Get test data object [ ].
     *
     * @return the object [ ]
     */
    Object[] getTestData(String dataBaseLocation);

    /**
     * The enum Data base type.
     */
    enum DataBaseType {
        XML,
        CSV,
        DB,
        XLS
    }
}