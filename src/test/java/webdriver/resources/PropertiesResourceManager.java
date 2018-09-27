package webdriver.resources;

import lombok.Cleanup;
import lombok.NonNull;
import lombok.SneakyThrows;
import webdriver.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * The type Properties resource manager.
 */
public final class PropertiesResourceManager {
    private static final Logger LOGGER = Logger.getInstance();
    private final Properties properties = new Properties();

    /**
     * Instantiates a new Properties resource manager.
     *
     * @param resourceName the resource name
     */
    public PropertiesResourceManager(@NonNull final String resourceName) {
        appendFromResource(resourceName);
    }

    /**
     * Gets property.
     *
     * @param key the key
     *
     * @return the property
     */
    public String getProperty(@NonNull final String key) {
        return this.properties.getProperty(key);
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
        return this.properties.getProperty(key, defaultValue);
    }

    /**
     * Sets property.
     *
     * @param key   the key
     * @param value the value
     */
    public void setProperty(final String key, final String value) {
        this.properties.setProperty(key, value);
    }

    @SneakyThrows(IOException.class)
    private void appendFromResource(final String resourceName) {
        @Cleanup InputStream inStream = this.getClass().getClassLoader().getResourceAsStream(resourceName);
        if (inStream != null) {
            this.properties.load(inStream);
        } else {
            LOGGER.error(String.format("Resource \"%1$s\" could not be found", resourceName));
        }
    }
}