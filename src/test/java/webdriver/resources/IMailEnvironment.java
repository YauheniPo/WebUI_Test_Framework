package webdriver.resources;

import org.aeonbits.owner.Config;

import static webdriver.resources.Constants.PROPERTIES_MAIL;

@Config.Sources({"classpath:" + PROPERTIES_MAIL})
public interface IMailEnvironment extends Config {

    String serv();

    int port();

    @Key("smtp.auth")
    boolean smtpAuth();

    @DefaultValue("Sent")
    String seltFolder();
}