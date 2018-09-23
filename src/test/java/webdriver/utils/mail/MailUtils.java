package webdriver.utils.mail;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.aeonbits.owner.ConfigFactory;
import webdriver.BaseEntity;
import webdriver.resources.IMailEnvironment;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Properties;

import static webdriver.resources.Constants.CHARSET;

/**
 * The type Mail utils.
 */
public class MailUtils extends BaseEntity {

    private final IMailEnvironment mailEnv = ConfigFactory.create(IMailEnvironment.class);
    private final Properties properties = new Properties();
    private String host;
    private final String fromAddress;
    private final String password;
    private MailProtocols protocol;
    private Store store;
    private Session session;
    private int port = mailEnv.port();
    private String service;
    private String sentFolder = mailEnv.seltFolder();
    private MimeMessage message;

    /**
     * Instantiates a new Mail utils.
     *
     * @param username the username
     * @param password the password
     */
    public MailUtils(String username, String password) {
        this.fromAddress = username;
        this.password = password;
        readConfig();
        setProperties();
        initSession();
        store = connect();
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
     */
    @SneakyThrows({MessagingException.class, IOException.class})
    public void sendMail() {
        Transport.send(message);
        LOGGER.info(String.format("Sent a email Subject: %s Text: %s", message.getSubject(), message.getContent()));
    }

    /**
     * Message generation mail utils.
     *
     * @param text                  the text
     * @param subject               the subject
     * @param recipientEmailAddress the recipient email address
     * @return the mail utils
     */
    @SneakyThrows({MessagingException.class})
    public MailUtils messageGeneration(String text, String subject, String recipientEmailAddress) {
        message = new MimeMessage(session);
        message.setHeader("Content-Type", String.format("text/plain; charset=%s", CHARSET));
        message.setSubject(subject, CHARSET);
        message.setText(text, CHARSET);
        message.setFrom(new InternetAddress(fromAddress));
        InternetAddress toAddress = new InternetAddress(recipientEmailAddress);
        message.addRecipient(Message.RecipientType.TO, toAddress);
        return this;
    }

    /**
     * Add msg in folder.
     *
     * @param folderName the folder name
     */
    @SneakyThrows({MessagingException.class})
    private void addMsgInFolder(String folderName) {
        Folder folder = getStoreConnected().getFolder(folderName);
        folder.open(Folder.READ_WRITE);
        folder.appendMessages(new Message[]{message});
        folder.close(true);
    }

    /**
     * Add msg in sent folder.
     */
    @SneakyThrows({MessagingException.class, IOException.class})
    public void addMsgInSentFolder() {
        LOGGER.info(String.format("Sent a email Subject: %s Text: %s to %s folder", this.message.getSubject(),
                           message.getContent(), sentFolder));
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
                LOGGER.debug(e.getMessage());
                try {
                    Thread.sleep(delay);
                } catch (InterruptedException e1) {
                    LOGGER.debug(e1.getMessage());
                }
                e.printStackTrace();
            } catch (MessagingException e) {
                LOGGER.debug(e.getMessage());
                try {
                    Thread.sleep(delay);
                } catch (InterruptedException e1) {
                    LOGGER.debug(e1.getMessage());
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
                LOGGER.info(String.format("Mails deleted from %s from %s folder", fromAddress, folder.getName()));
            }
        } catch (MessagingException e) {
            LOGGER.debug(e.getMessage());
        }
    }

    /**
     * Close store.
     */
    @SneakyThrows(Exception.class)
    public void closeStore() {
        store.close();
        LOGGER.info(String.format("MailStore %s is close", fromAddress));
    }

    /**
     * Read config.
     */
    private void readConfig() {
        String prop = mailEnv.serv();
        String hostProtoc = prop.split(";")[0];
        service = prop.split(";")[1];
        host = String.format("%s.%s", hostProtoc, service);
        protocol = MailProtocols.valueOf(prop.split(";")[2].toUpperCase());
    }

    /**
     * Fetch properties.
     */
    private void setProperties() {
        properties.setProperty("mail.store.protocol", protocol.toString());
        properties.put("mail.smtp.host", String.format("smtp.%s", service));
        properties.put("mail.smtp.auth", mailEnv.smtpAuth());
        properties.put("mail.smtp.port", port);
        properties.put("mail.imap.ssl.enable", "true");
        properties.put("mail.smtp.socketFactory.port", port);
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
    }

    /**
     * The enum Mail protocols.
     */
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    public enum MailProtocols {
        POP3("pop3"),
        SMTP("smtp"),
        IMAP("imap"),
        IMAPS("imaps"),
        POP3S("pop3s");

        private final String protocol;

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