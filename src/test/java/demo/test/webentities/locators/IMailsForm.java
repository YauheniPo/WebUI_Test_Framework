package demo.test.webentities.locators;

import org.aeonbits.owner.Config;

import static demo.test.webentities.locators.Constants.LOCATORS_PROPERTIES_DIRECTORY;
import static demo.test.webentities.locators.Constants.MAILS_FORM_LOCATORS;

@Config.Sources({"classpath:" + LOCATORS_PROPERTIES_DIRECTORY + "/" + MAILS_FORM_LOCATORS})
public interface IMailsForm extends Config {

    @Key("main_locator")
    String mainLocator();

    @Key("last_mail")
    String lastMail();

    @Key("sender")
    String sender();

    @Key("recipient")
    String recipient();

    @Key("subject")
    String subject();

    @Key("text")
    String text();
}