package webdriver.utils.mail;

import javax.mail.PasswordAuthentication;

public class EmailAuthenticator extends javax.mail.Authenticator {
    private String login;
    private String password;

    EmailAuthenticator(final String login, final String password) {
        this.login = login;
        this.password = password;
    }

    public PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(login, password);
    }
}