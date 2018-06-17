package webdriver.utils.mail;

import javax.mail.PasswordAuthentication;

/**
 * The type Email authenticator.
 */
public class EmailAuthenticator extends javax.mail.Authenticator {
    private String login;
    private String password;

    /**
     * Instantiates a new Email authenticator.
     *
     * @param login    the login
     * @param password the password
     */
    EmailAuthenticator(final String login, final String password) {
        this.login = login;
        this.password = password;
    }

    /**
     * Gets password authentication.
     *
     * @return the password authentication
     */
    public PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(login, password);
    }
}