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
    }

    /**
     * Gets store connected.
     *
     * @return the store connected
     */
    @SneakyThrows(InterruptedException.class)
    private Store getStoreConnected() {
        if (this.store == null || !this.store.isConnected()) {
            this.store = connect();
        }
        return this.store;
    }

    /**
     * Init session.
     */
    private void initSession() {
        Authenticator auth = new EmailAuthenticator(this.fromAddress, this.password);
        this.session = Session.getInstance(this.properties, auth);
    }

    /**
     * Send mail.
     */
    @SneakyThrows({MessagingException.class, IOException.class})
    public void sendMail() {
        Transport.send(this.message);
        LOGGER.info(String.format("Sent a email Subject: %s Text: %s", this.message.getSubject(), this.message.getContent()));
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
        this.message = new MimeMessage(this.session);
        this.message.setHeader("Content-Type", String.format("text/plain; charset=%s", CHARSET));
        this.message.setSubject(subject, CHARSET);
        this.message.setText(text, CHARSET);
        this.message.setFrom(new InternetAddress(this.fromAddress));
        InternetAddress toAddress = new InternetAddress(recipientEmailAddress);
        this.message.addRecipient(Message.RecipientType.TO, toAddress);
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
        folder.appendMessages(new Message[]{this.message});
        folder.close(true);
    }

    /**
     * Add msg in sent folder.
     */
    @SneakyThrows({MessagingException.class, IOException.class})
    public void addMsgInSentFolder() {
        LOGGER.info(String.format("Sent a email Subject: %s Text: %s to %s folder", this.message.getSubject(),
                this.message.getContent(), this.sentFolder));
        addMsgInFolder(this.sentFolder);
    }

    /**
     * Connect store.
     *
     * @return the store
     */
    private Store connect() throws InterruptedException {
        int delay = 1000;
        for (int i = 0; i <= 10; ++i) {
            try {
                this.store = this.session.getStore();
                this.store.connect(this.host, this.fromAddress, this.password);
                break;
            } catch (MessagingException e) {
                LOGGER.debug(e.getMessage());
                Thread.sleep(delay);
            }
        }
        return this.store;
    }

    /**
     * Delete all messages.
     */
    public void deleteAllMessages() {
        try {
            Folder[] folders = getStoreConnected().getDefaultFolder().list("*");
            for (Folder folder : folders) {
                folder.open(Folder.READ_WRITE);
                Message[] messages = folder.getMessages();
                for (Message message : messages) {
                    message.setFlag(Flags.Flag.DELETED, true);
                }
                folder.close(true);
                LOGGER.info(String.format("Mails deleted from %s from %s folder", this.fromAddress, folder.getName()));
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
        this.store.close();
        LOGGER.info(String.format("MailStore %s is close", this.fromAddress));
    }

    /**
     * Read config.
     */
    private void readConfig() {
        String prop = this.mailEnv.serv();
        String hostProtoc = prop.split(";")[0];
        this.service = prop.split(";")[1];
        this.host = String.format("%s.%s", hostProtoc, this.service);
        this.protocol = MailProtocols.valueOf(prop.split(";")[2].toUpperCase());
    }

    /**
     * Fetch properties.
     */
    private void setProperties() {
        this.properties.setProperty("mail.store.protocol", this.protocol.toString());
        this.properties.put("mail.smtp.host", String.format("smtp.%s", this.service));
        this.properties.put("mail.smtp.auth", this.mailEnv.smtpAuth());
        this.properties.put("mail.smtp.port", this.port);
        this.properties.put("mail.imap.ssl.enable", "true");
        this.properties.put("mail.smtp.socketFactory.port", this.port);
        this.properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
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