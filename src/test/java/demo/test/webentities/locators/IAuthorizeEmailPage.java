package demo.test.webentities.locators;

import org.aeonbits.owner.Config;

import static demo.test.webentities.locators.Constants.AUTHORIZE_EMAIL_PAGE_LOCATORS;
import static demo.test.webentities.locators.Constants.LOCATORS_PROPERTIES_DIRECTORY;

@Config.Sources({"classpath:" + LOCATORS_PROPERTIES_DIRECTORY + "/" + AUTHORIZE_EMAIL_PAGE_LOCATORS})
public interface IAuthorizeEmailPage extends Config {

    @Key("main_locator")
    String mainLocator();

    @Key("text_box_input_login")
    String textBoxInputLogin();

    @Key("text_box_input_password")
    String textBoxInputPassword();

    @Key("btn_sigh_in")
    String btnSighIn();

    @Key("info_panel")
    String infoPanel();

    @Key("checkbox_memory")
    String checkboxMemory();

    @Key("login_form")
    String loginForm();
}