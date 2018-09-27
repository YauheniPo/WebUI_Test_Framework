package webdriver.resources;

import org.aeonbits.owner.Config;

import static webdriver.resources.Constants.PROPERTIES_SELENIUM;

@Config.Sources(value = {"classpath:" + PROPERTIES_SELENIUM})
public interface IDriverEnvironment extends Config {

    @DefaultValue("3000")
    Integer defaultConditionTimeout();

    @DefaultValue("1000")
    Integer implicityWait();

    @DefaultValue("6000")
    Integer pageImplicityWait();

    @DefaultValue("chrome")
    String browser();

    @DefaultValue("true")
    Boolean browserHeadless();

    @DefaultValue("false")
    Boolean grid();

    @DefaultValue("http://${gridIp}:${gridPort}/wd/hub")
    String gridUrl();
}