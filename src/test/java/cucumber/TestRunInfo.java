package cucumber;

import static webdriver.resources.Constants.PROPERTIES_TEST_INFO;

import webdriver.resources.PropertiesResourceManager;

public class TestRunInfo {
    private static final webdriver.resources.PropertiesResourceManager propsTestInfo = new PropertiesResourceManager(PROPERTIES_TEST_INFO);
    private static volatile TestRunInfo testRunInstance = null;

    private TestRunInfo() {
    }

    public static TestRunInfo getInstance() {
        if (testRunInstance == null) {
            synchronized (propsTestInfo) {
                if (testRunInstance == null) {
                    testRunInstance = new TestRunInfo();
                }
            }
        }
        return testRunInstance;
    }

    public PropertiesResourceManager getTestInfoProperties() {
        return propsTestInfo;
    }
}

