package webdriver;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * The type Properties resource manager.
 */
public final class PropertiesResourceManager {
    private static final Logger logger = Logger.getInstance();
    private Properties properties;

    /**
     * Instantiates a new Properties resource manager.
     *
     * @param resourceName the resource name
     */
    public PropertiesResourceManager(final String resourceName) {
        properties= new Properties();
        properties = appendFromResource(properties, resourceName);
    }

    /**
     * Gets property.
     *
     * @param key the key
     *
     * @return the property
     */
    public String getProperty(final String key) {
        return properties.getProperty(key);
    }

    /**
     * Gets property.
     *
     * @param key          the key
     * @param defaultValue the default value
     *
     * @return the property
     */
    public String getProperty(final String key, final String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }

    /**
     * Sets property.
     *
     * @param key   the key
     * @param value the value
     */
    public void setProperty(final String key, final String value) {
        properties.setProperty(key, value);
    }

    private Properties appendFromResource(final Properties objProperties, final String resourceName) {
        InputStream inStream = this.getClass().getClassLoader().getResourceAsStream(resourceName);

        if (inStream != null) {
            try {
                objProperties.load(inStream);
                inStream.close();
            } catch (IOException e) {
                logger.info(e.getMessage());
            }
        } else {
            logger.error(String.format("Resource \"%1$s\" could not be found", resourceName));
        }
        return objProperties;
    }
}