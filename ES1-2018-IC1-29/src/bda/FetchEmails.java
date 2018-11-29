package bda;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Properties;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Acede à caixa de entrada de uma conta de email tendo em conta que o username
 * e password corretos são fornecidos
 * 
 * @author Grupo 29
 * @version 2.0
 */
public class FetchEmails {

	private ArrayList<Content> msgs = new ArrayList<Content>();
	private Properties props = new Properties();
	private Session session;
	private Store store;
	private Folder inbox;
	private String username;
	private String password;

	/**
	 * Método que devolve um arraylist de Conteúdos
	 * 
	 * @return msgs ArrayList<Content> (mensagens que constam no mail do utilizador)
	 */
	public ArrayList<Content> getMsgs() {
		return msgs;
	}



	public void setProperties() {
		props.setProperty("mail.store.protocol", "imaps");
		props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");
	}

	/**
	 * Método que confirma se o username e password fornecidos estão corretos e
	 * acede à conta de email
	 * 
	 * @param username String (username do utilizador)
	 * @param password String (password do utilizador)
	 */
	public void checkMail(String username, String password) {
		  setProperties();
        	this.username = username;
        	this.password = password;
		try {
			Session session = Session.getInstance(props, null);
			Store store = session.getStore();
			try {
				store.connect("imap.gmail.com", username, password);
			} catch (Exception e) { // descobrir nome
				System.out.println("invalid user and pass");
				return;
			}
			inbox = store.getFolder("INBOX");
			inbox.open(Folder.READ_ONLY);

			for (int i = 1; i <= inbox.getMessages().length; i++) {
				msgs.add(new Content (inbox.getMessage(i)));
			}
		
			for (Content c : msgs) {
				String hash = c.getHashCode();
				PrintWriter writer = new PrintWriter(
						System.getProperty("user.dir") + File.separator + "Resources\\Emails\\Email" + hash, "UTF-8");
				Message b = (Message) c.getContent();
				writer.println(b.getContent());
				writer.close();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void sendEmail (String to, String from, String subject, String text) {
//		String to = "es1.grupo29@gmail.com";
//		String from = "es1.grupo29@gmail.com";
//		String username = "es1.grupo29@gmail.com";
//		String password = "tenhosono";

	      // Get the Session object.
	      Session session = Session.getInstance(props,
	         new javax.mail.Authenticator() {
	            protected PasswordAuthentication getPasswordAuthentication() {
	               return new PasswordAuthentication(username, password);
		   }
	         });

	      try {
	   
		   // Create a default MimeMessage object.
		   Message message = new MimeMessage(session);
		
		   // Set From: header field of the header.
		   message.setFrom(new InternetAddress(from));
		
		   // Set To: header field of the header.
		   message.setRecipients(Message.RecipientType.TO,
	               InternetAddress.parse(to));
		
		   // Set Subject: header field
		   message.setSubject(subject);
		
		   // Now set the actual message
		   message.setText(text);

		   // Send message
		   Transport.send(message);

		   System.out.println("Sent message successfully....");

	      } catch (MessagingException e) {
	         throw new RuntimeException(e);
	      }
		
	}


}


