package demo.test.webentities.locators;

import org.aeonbits.owner.Config;

import static demo.test.webentities.locators.Constants.LOCATORS_PROPERTIES_DIRECTORY;
import static demo.test.webentities.locators.Constants.TUT_BY_HOME_PAGE_LOCATORS;

@Config.Sources({"classpath:" + LOCATORS_PROPERTIES_DIRECTORY + "/" + TUT_BY_HOME_PAGE_LOCATORS})
public interface ITutByHomePage extends Config {

    @Key("main_locator")
    String mainLocator();
}