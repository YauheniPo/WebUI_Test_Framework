package webdriver;

import lombok.Cleanup;
import lombok.NonNull;
import lombok.SneakyThrows;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * The type Properties resource manager.
 */
public final class PropertiesResourceManager {
    private static final Logger LOGGER = Logger.getInstance();
    private Properties properties;

    /**
     * Instantiates a new Properties resource manager.
     *
     * @param resourceName the resource name
     */
    public PropertiesResourceManager(@NonNull final String resourceName) {
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
    public String getProperty(@NonNull final String key) {
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

    @SneakyThrows(IOException.class)
    private Properties appendFromResource(final Properties objProperties, final String resourceName) {
        @Cleanup InputStream inStream = this.getClass().getClassLoader().getResourceAsStream(resourceName);
        if (inStream != null) {
            objProperties.load(inStream);
        } else {
            LOGGER.error(String.format("Resource \"%1$s\" could not be found", resourceName));
        }
        return objProperties;
    }
}