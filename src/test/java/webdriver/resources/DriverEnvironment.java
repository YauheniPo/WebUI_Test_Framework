package webdriver.resources;

import org.aeonbits.owner.Config;

import static webdriver.resources.Constants.PROPERTIES_SELENIUM;
import static webdriver.resources.Constants.PROPERTIES_STAGE;

@Config.Sources({"classpath:" + PROPERTIES_SELENIUM,
        "classpath:" + PROPERTIES_STAGE})
public interface DriverEnvironment extends Config {

    Integer defaultConditionTimeout();

    Integer implicityWait();

    String browser();

    Boolean driverManager();

    Boolean browserHeadless();

    Boolean grid();

    String stage();
}