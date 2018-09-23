package demo.test.webentities.locators;

import org.aeonbits.owner.Config;

import static demo.test.webentities.locators.Constants.ACCOUNT_FORM_LOCATORS;
import static demo.test.webentities.locators.Constants.LOCATORS_PROPERTIES_DIRECTORY;

@Config.Sources({"classpath:" + LOCATORS_PROPERTIES_DIRECTORY + "/" + ACCOUNT_FORM_LOCATORS})
public interface IAccountForm extends Config {

    @Key("main_locator")
    String mainLocator();

    @Key("user_dropdown")
    String userDropdown();
}