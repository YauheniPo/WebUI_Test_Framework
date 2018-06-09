package demo.test.utils;

import webdriver.Logger;

import java.util.List;

public abstract class InitParams {
    private String senderMailLogin;
    private String senderMailPassword;
    private String recipientMailLogin;
    private String recipientMailPassword;
    Logger LOGGER = Logger.getInstance();

    void setParams(List<String> params) {
        senderMailLogin = params.get(0);
        senderMailPassword = params.get(1);
        recipientMailLogin = params.get(2);
        recipientMailPassword = params.get(3);
    }

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