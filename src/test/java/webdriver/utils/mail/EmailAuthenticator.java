package webdriver.utils.mail;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import javax.mail.PasswordAuthentication;

/**
 * The type Email authenticator.
 */
@AllArgsConstructor(access = AccessLevel.MODULE)
class EmailAuthenticator extends javax.mail.Authenticator {
    private final String login;
    private final String password;

    /**
     * Gets password authentication.
     *
     * @return the password authentication
     */
    public PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(this.login, this.password);
    }
}