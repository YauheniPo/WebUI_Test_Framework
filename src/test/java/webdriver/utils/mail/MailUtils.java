package webdriver.utils.mail;

import webdriver.BaseEntity;
import webdriver.PropertiesResourceManager;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

import static webdriver.ConstantsFrm.CHARSET;
import static webdriver.ConstantsFrm.PROPERTIES_MAIL;

/**
 * The type Mail utils.
 */
public class MailUtils extends BaseEntity {
    private static final PropertiesResourceManager props = new PropertiesResourceManager(PROPERTIES_MAIL);
    private String host;
    private final String fromAddress;
    private final String password;
    private final Properties properties = new Properties();
    private MailProtocols protocol;
    private Store store;
    private Session session;
    private String port;
    private String service;
    private String sentFolder;
    private MimeMessage message;

    /**
     * Instantiates a new Mail utils.
     *
     * @param host     the host
     * @param username the username
     * @param password the password
     */
    private MailUtils(String host, String username, String password) {
        this.fromAddress = username;
        this.password = password;
        readConfig(host);
        fetchProperties();
        initSession();
        store = connect();
    }

    /**
     * Instantiates a new Mail utils.
     *
     * @param account  the account
     * @param password the password
     */
    public MailUtils(String account, String password) {
        this(account.split("@")[1], account, password);
    }

    /**
     * Gets store connected.
     *
     * @return the store connected
     */
    private Store getStoreConnected() {
        if (store.isConnected()) {
            return store;
        }
        store = connect();
        return store;
    }

    /**
     * Init session.
     */
    private void initSession() {
        Authenticator auth = new EmailAuthenticator(fromAddress, password);
        session = Session.getInstance(properties, auth);
    }

    /**
     * Send mail.
     *
     * @param text                  the text
     * @param subject               the subject
     * @param recipientEmailAddress the recipient emsil address
     */
    public void sendMail(String text, String subject, String recipientEmailAddress) {
        message = new MimeMessage(session);
        try {
            message.setHeader("Content-Type", String.format("text/plain; charset=%s", CHARSET));
            message.setSubject(subject, CHARSET);
            message.setText(text, CHARSET);
            message.setFrom(new InternetAddress(fromAddress));
            InternetAddress toAddress = new InternetAddress(recipientEmailAddress);
            message.addRecipient(Message.RecipientType.TO, toAddress);
            Transport.send(message);
            info(String.format("Sent a email Subject: %s Text: %s", subject, text));
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    /**
     * Add msg in folder.
     *
     * @param folderName the folder name
     */
    private void addMsgInFolder(String folderName) {
        try {
            Folder folder = getStoreConnected().getFolder(folderName);
            folder.open(Folder.READ_WRITE);
            folder.appendMessages(new Message[]{message});
            folder.close(true);
        } catch (MessagingException e) {
            debug(e.getMessage());
        }
    }

    /**
     * Add msg in sent folder.
     */
    public void addMsgInSentFolder() {
        addMsgInFolder(sentFolder);
    }

    /**
     * Connect store.
     *
     * @return the store
     */
    private Store connect() {
        int delay = 1000;
        for (int i = 0; i <= 10; i++) {
            initSession();
            try {
                store = session.getStore();
                store.connect(host, fromAddress, password);
                break;
            } catch (NoSuchProviderException e) {
                debug(e.getMessage());
                try {
                    Thread.sleep(delay);
                } catch (InterruptedException e1) {
                    debug(e1.getMessage());
                }
                e.printStackTrace();
            } catch (MessagingException e) {
                debug(e.getMessage());
                try {
                    Thread.sleep(delay);
                } catch (InterruptedException e1) {
                    debug(e1.getMessage());
                }
            }
        }
        return store;
    }

    /**
     * Delete all messages.
     */
    public void deleteAllMessages() {
        try {
            Folder[] folders = store.getDefaultFolder().list("*");
            for (Folder folder : folders) {
                folder.open(Folder.READ_WRITE);
                Message[] messages = folder.getMessages();
                for (Message message : messages) {
                    message.setFlag(Flags.Flag.DELETED, true);
                }
                folder.close(true);
                info(String.format("Mails deleted from %s from %s folder", fromAddress, folder.getName()));
            }
        } catch (MessagingException e) {
            debug(e.getMessage());
        }
    }

    /**
     * Close store.
     */
    public void closeStore() {
        try {
            store.close();
            info(String.format("MailStore %s is close", fromAddress));
        } catch (Exception e) {
            debug(e.getMessage());
        }
    }

    /**
     * Read config.
     *
     * @param host the host
     */
    private void readConfig(String host) {
        String prop = props.getProperty(host);
        String hostProtoc = prop.split(";")[0];
        this.service = prop.split(";")[1];
        this.host = String.format("%s.%s", hostProtoc, service);
        this.port = props.getProperty("port");
        this.sentFolder = props.getProperty("seltFolder", "Sent");
        this.protocol = MailProtocols.valueOf(prop.split(";")[2].toUpperCase());
    }

    /**
     * Fetch properties.
     */
    private void fetchProperties() {
        properties.setProperty("mail.store.protocol", protocol.toString());
        properties.put("mail.smtp.host", String.format("smtp.%s", service));
        properties.put("mail.smtp.auth", props.getProperty("smtp.auth"));
        properties.put("mail.smtp.port", port);
        properties.put("mail.imap.ssl.enable", "true");
        properties.put("mail.smtp.socketFactory.port", port);
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
    }

    /**
     * The enum Mail protocols.
     */
    public enum MailProtocols {
        POP3("pop3"),
        SMTP("smtp"),
        IMAP("imap"),
        IMAPS("imaps"),
        POP3S("pop3s");

        private final String protocol;

        /**
         * Instantiates a new Mail protocols.
         *
         * @param name the name
         */
        MailProtocols(String name) {
            protocol = name;
        }

        /**
         * To string string.
         *
         * @return the string
         */
        @Override
        public String toString() {
            return protocol;
        }
    }
}