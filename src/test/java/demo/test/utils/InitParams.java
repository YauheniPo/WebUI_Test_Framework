package demo.test.utils;

import webdriver.Logger;

public abstract class InitParams {
    String senderMailLogin;
    String senderMailPassword;
    String recipientMailLogin;
    String recipientMailPassword;
    Logger LOGGER = Logger.getInstance();

    public abstract InitParams fetchTestData(String dataBaselocation);

    public String getSenderMailLogin() {
        return senderMailLogin;
    }

    public String getSenderMailPassword() {
        return senderMailPassword;
    }

    public String getRecipientMailLogin() {
        return recipientMailLogin;
    }

    public String getRecipientMailPassword() {
        return recipientMailPassword;
    }
}