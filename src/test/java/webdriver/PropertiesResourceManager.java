package webdriver;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class PropertiesResourceManager {
    private static final Logger logger = Logger.getInstance();
	private Properties properties = new Properties();

	public PropertiesResourceManager(final String resourceName) {
		properties = appendFromResource(properties, resourceName);
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

	public String getProperty(final String key) {
		return properties.getProperty(key);
	}

	public String getProperty(final String key, final String defaultValue) {
		return properties.getProperty(key, defaultValue);
	}

	public void setProperty(final String key, final String value) {
		properties.setProperty(key, value);
	}
}