package webdriver.resources;

import org.aeonbits.owner.Config;

import static webdriver.resources.Constants.PROPERTIES_STAGE;

@Config.Sources(value = {"classpath:" + PROPERTIES_STAGE})
public interface IStageEnvironment extends Config {

    @DefaultValue("http://${stage}")
    String urlLoginPage();
}