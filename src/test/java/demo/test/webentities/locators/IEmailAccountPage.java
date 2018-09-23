package demo.test.webentities.locators;

import org.aeonbits.owner.Config;

import static demo.test.webentities.locators.Constants.EMAIL_ACCOUNT_PAGE_LOCATORS;
import static demo.test.webentities.locators.Constants.LOCATORS_PROPERTIES_DIRECTORY;

@Config.Sources({"classpath:" + LOCATORS_PROPERTIES_DIRECTORY + "/" + EMAIL_ACCOUNT_PAGE_LOCATORS})
public interface IEmailAccountPage extends Config {

    @Key("main_locator")
    String mainLocator();

    @Key("nav_bar")
    String navBar();
}