package demo.test.utils;

import webdriver.Logger;

import java.util.List;

/**
 * The type Init parameters.
 */
public abstract class InitParams {
    private String senderMailLogin;
    private String senderMailPassword;
    private String recipientMailLogin;
    private String recipientMailPassword;

    /**
     * The Logger.
     */
    Logger LOGGER = Logger.getInstance();

    /**
     * Sets parameters.
     *
     * @param params the parameters
     */
    void setParams(List<String> params) {
        senderMailLogin = params.get(0);
        senderMailPassword = params.get(1);
        recipientMailLogin = params.get(2);
        recipientMailPassword = params.get(3);
    }

    /**
     * Fetch test data init parameters.
     *
     * @param dataBaselocation the data baselocation
     *
     * @return the init params
     */
    public abstract InitParams fetchTestData(String dataBaselocation);

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