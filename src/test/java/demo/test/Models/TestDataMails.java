package demo.test.models;

public class TestDataMails {
    private final String senderMailLogin;
    private final String senderMailPassword;
    private final String recipientMailLogin;
    private final String recipientMailPassword;

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