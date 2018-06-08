package webdriver.utils.mail;

import com.sun.mail.imap.IMAPMessage;
import webdriver.BaseEntity;
import webdriver.PropertiesResourceManager;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Properties;

import static webdriver.ConstantsFrm.CHARSET;
import static webdriver.ConstantsFrm.PROPERTIES_MAIL;

public class MailUtils extends BaseEntity {
	private static final PropertiesResourceManager props = new PropertiesResourceManager(PROPERTIES_MAIL);
	private String host, fromAddress, password;
	private Properties properties = new Properties();
	private MAIL_PROTOCOLS protocol;
	private Store store;
	private Session session;
	private String port;
	private String hostProtoc;
	private String service;
	private String sentFolder;
	private MimeMessage message;

	public MailUtils(String host, String username, String password) {
	    this.fromAddress = username;
	    this.password = password;
	    readConfig(host);
		fetchProperties();
		initSession();
	    store = connect();
	}

	public MailUtils(String account, String password){
		this(account.split("@")[1], account, password);
	}

	public enum MAIL_PROTOCOLS{
		POP3("pop3"), SMTP("smtp"), IMAP("imap"), IMAPS("imaps"), POP3S("pop3s");

		private String protocol;

		MAIL_PROTOCOLS(String name){
			protocol = name;
		}

		@Override
		public String toString() {
			return protocol;
		}
	}

	private Store getStoreConnected() {
		if (store.isConnected()) {
			return store;
		}
		store = connect();
		return store;
	}

	private void initSession() {
		Authenticator auth = new EmailAuthenticator(fromAddress, password);
		session = Session.getInstance(properties, auth);
	}

	public void sendMail(String text, String subject, String recipientEmsilAddress) {
        message = new MimeMessage(session);
        try {
			message.setHeader("Content-Type", String.format("text/plain; charset=%s", CHARSET));
			message.setSubject(subject, CHARSET);
			message.setText(text, CHARSET);
			message.setFrom(new InternetAddress(fromAddress));
			InternetAddress toAddress = new InternetAddress(recipientEmsilAddress);
			message.addRecipient(Message.RecipientType.TO, toAddress);
			Transport.send(message);
            info(String.format("Sent a email Subject: %s Text: %s", subject, text));
        } catch (MessagingException e) {
            e.printStackTrace();
        }
	}

	public Message[] getInboxMessages(){
		return getMessages("INBOX");
	}

	public Message[] getMessages(Folder folder, int permission){
    	try {
    		folder.open(permission);
			return folder.getMessages();
		} catch (MessagingException e) {
			info(e.getMessage());
		}
	    return null;
	}

	public Message[] getMessages(Folder folder){
		return getMessages(folder, Folder.READ_WRITE);
	}
	
	public Message[] getMessages(String folderName){
		try {
			return getMessages(getStoreConnected().getFolder(folderName), Folder.READ_WRITE);
		} catch (MessagingException e) {
			info(e.getMessage());
		}
		return null;
	}

	public void addMsgInFolder(String folderName) {
		try{
			Folder folder = getStoreConnected().getFolder(folderName);
			folder.open(Folder.READ_WRITE);
			folder.appendMessages(new Message[] {message});
			folder.close(true);
		}catch(MessagingException e){
			debug(e.getMessage());
		}
	}

	public void addMsgInSentFolder(){
		addMsgInFolder(sentFolder);
	}

	private Store connect(){
		int delay = 1000;
		for(int i = 0; i <= 10; i++){
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
			}
			catch (MessagingException e) {
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

	public void deleteFolderMessages(String folderName){
		try{
			Folder folder = getStoreConnected().getFolder(folderName);
			folder.open(Folder.READ_WRITE);
			Message[] messages = folder.getMessages();
			for(Message message:messages) {
				message.setFlag(Flags.Flag.DELETED, true);
			}
			folder.close(true);
		}catch(MessagingException e){
            debug(e.getMessage());
		}
	}

	public void deleteAllMessages(){
		try{
			Folder[] folders = store.getDefaultFolder().list("*");
			for (Folder folder : folders) {
				folder.open(Folder.READ_WRITE);
				Message[] messages = folder.getMessages();
				for(Message message : messages) {
					message.setFlag(Flags.Flag.DELETED, true);
				}
				folder.close(true);
			}
		}catch(MessagingException e){
			debug(e.getMessage());
		}
	}

	public Boolean isConnected(){
		return store.isConnected();
	}

	public void closeStore(){
		try {
			store.close();
			info("MailStore is close");
		} catch (Exception e) {
            debug(e.getMessage());
		}
	}
	
	private void readConfig(String host){
		String prop = props.getProperty(host);
		this.hostProtoc = prop.split(";")[0];
		this.service = prop.split(";")[1];
		this.host = String.format("%s.%s", hostProtoc, service);
		this.port = props.getProperty("port");
		this.sentFolder = props.getProperty("seltFolder", "Sent");
		this.protocol = MAIL_PROTOCOLS.valueOf(prop.split(";")[2].toUpperCase());
	}

	private void fetchProperties() {
		properties.setProperty("mail.store.protocol", protocol.toString());
		properties.put("mail.smtp.host", String.format("smtp.%s", service));
		properties.put("mail.smtp.auth", props.getProperty("smtp.auth"));
		properties.put("mail.smtp.port", port);
		properties.put("mail.imap.ssl.enable", "true");
		properties.put("mail.smtp.socketFactory.port", port);
		properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
	}

    public static String getMsgText(Message msg) {
		try {
			return msg.getContent().toString();
		} catch (IOException | MessagingException e) {
			e.printStackTrace();
		}
		return null;
	}
}