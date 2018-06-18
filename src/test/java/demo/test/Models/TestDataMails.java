package demo.test.Models;

public class TestDataMails {
    private String senderMailLogin;
    private String senderMailPassword;
    private String recipientMailLogin;
    private String recipientMailPassword;

    /**
     * Instantiates a new Test data mails.
     *
     * @param senderMailLogin       the sender mail login
     * @param senderMailPassword    the sender mail password
     * @param recipientMailLogin    the recipient mail login
     * @param recipientMailPassword the recipient mail password
     */
    public TestDataMails(String senderMailLogin, String senderMailPassword, String recipientMailLogin, String recipientMailPassword) {
        this.senderMailLogin = senderMailLogin;
        this.senderMailPassword = senderMailPassword;
        this.recipientMailLogin = recipientMailLogin;
        this.recipientMailPassword = recipientMailPassword;
    }

    /**
     * Gets sender mail login.
     *
     * @return the sender mail login
     */
    public String getSenderMailLogin() {
        return senderMailLogin;
    }

    /**
     * Gets sender mail password.
     *
     * @return the sender mail password
     */
    public String getSenderMailPassword() {
        return senderMailPassword;
    }

    /**
     * Gets recipient mail login.
     *
     * @return the recipient mail login
     */
    public String getRecipientMailLogin() {
        return recipientMailLogin;
    }

    /**
     * Gets recipient mail password.
     *
     * @return the recipient mail password
     */
    public String getRecipientMailPassword() {
        return recipientMailPassword;
    }
}