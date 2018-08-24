package webdriver.resources;

import org.aeonbits.owner.Config;

@Config.Sources({"classpath:mail.properties"})
public interface MailEnvironment extends Config {

    String serv();

    int port();

    @Key("smtp.auth")
    boolean smtpAuth();

    String seltFolder();
}