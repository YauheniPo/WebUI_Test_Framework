package webdriver;

import org.aeonbits.owner.Config;

@Config.Sources({"classpath:selenium.properties", "classpath:stage.properties"})
public interface Environment extends Config {

    String browser();

    Boolean driverManager();

    Boolean browserHeadless();

    Integer defaultConditionTimeout();

//    @Key("implicity.wait")
    Integer implicityWait();

    Boolean grid();

    String stage();
}