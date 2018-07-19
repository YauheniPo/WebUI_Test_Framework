package webdriver.utils.mail;

import javax.mail.PasswordAuthentication;

/**
 * The type Email authenticator.
 */
class EmailAuthenticator extends javax.mail.Authenticator {
    private final String login;
    private final String password;

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