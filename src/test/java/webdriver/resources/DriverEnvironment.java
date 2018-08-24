package webdriver.resources;

import org.aeonbits.owner.Config;

@Config.Sources({"classpath:selenium.properties", "classpath:stage.properties"})
public interface DriverEnvironment extends Config {

    String browser();

    Boolean driverManager();

    Boolean browserHeadless();

    Integer defaultConditionTimeout();

    Integer implicityWait();

    Boolean grid();

    String stage();
}