package webdriver.common;

import webdriver.Logger;

/**
 * The type Provider data.
 */
public interface ProviderData {
    Logger LOGGER = Logger.getInstance();

    /**
     * Get test data object [ ].
     *
     * @return the object [ ]
     */
    Object[] getTestData(String dataBaseLocation);
}