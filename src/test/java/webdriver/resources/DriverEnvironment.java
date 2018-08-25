package webdriver.resources;

import org.aeonbits.owner.Config;

import static webdriver.resources.Constants.PROPERTIES_SELENIUM;

@Config.Sources(value = {"classpath:" + PROPERTIES_SELENIUM})
public interface DriverEnvironment extends Config {

    @DefaultValue("30")
    Integer defaultConditionTimeout();

    @DefaultValue("10")
    Integer implicityWait();

    @DefaultValue("chrome")
    String browser();

    @DefaultValue("true")
    Boolean driverManager();

    @DefaultValue("true")
    Boolean browserHeadless();

    @DefaultValue("false")
    Boolean grid();

    @DefaultValue("http://${gridIp}:${gridPort}/wd/hub")
    String gridUrl();
}