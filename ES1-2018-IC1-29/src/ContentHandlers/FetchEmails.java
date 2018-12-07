package ContentHandlers;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import bda.Content;

/**
 * Acede à caixa de entrada de uma conta de Email tendo em conta que o username
 * e password corretos são fornecidos
 * 
 * @author Grupo 29
 * @version 4.0
 */
public class FetchEmails {

	private ArrayList<Content> msgs = new ArrayList<Content>();
	private String userName;
	private Properties props = new Properties();
	private Session session;
	private Store store;
	private Folder inbox;
	private PasswordAuthentication p;
	@SuppressWarnings("unused")
	private boolean textIsHTML = false;

	/**
	 * Método que devolve um arraylist de Conteúdos
	 * 
	 * @return msgs ArrayList<Content> (mensagens que constam no mail do utilizador)
	 */
	public ArrayList<Content> getMsgs() {
		return msgs;
	}

	/**
	 * Método que define as propriedades de acesso à caixa de email
	 */
	public void setProperties() {
		props.setProperty("mail.store.protocol", "imaps");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");
	}

	/**
	 * Método que valida o username e password, inicia uma sessão e acede à inbox do
	 * utilizador
	 * 
	 * @param userName String(username)
	 * @param password String(password)
	 */
	public void connect(String userName, String password) throws MessagingException {
		setProperties();
		this.userName = userName;
		p = new PasswordAuthentication(userName, password);
		Authenticator a = new Authenticator() {
			@Override
			protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
				// TODO Auto-generated method stub
				return p;
			};
		};
		session = Session.getInstance(props, a);
		store = session.getStore();
		store.connect("imap.gmail.com", userName, password);
		inbox = store.getFolder("INBOX");
		inbox.open(Folder.READ_ONLY);
	}

	/**
	 * Método que acede à conta de email e guarda as mensagens localmente
	 * 
	 * @throws IOException
	 * @throws MessagingException
	 */
	public void checkMail() throws MessagingException, IOException {

		for (int i = 1; i <= inbox.getMessages().length; i++) {
			msgs.add(new Content(inbox.getMessage(i), userName));
		}

		for (Content c : msgs) {
			String hash = c.getHashCode();
			PrintWriter writer = new PrintWriter(
					System.getProperty("user.dir") + File.separator + "Resources\\Emails\\Email" + hash, "UTF-8");
			Message b = (Message) c.getContent();
			writer.println("no ID");
			writer.println("Email");
			writer.println(userName);
			writer.println(b.getSentDate());
			writer.println(((InternetAddress) b.getFrom()[0]).getAddress());
			writer.println(b.getSubject());

			Multipart mp;
			if (!(b.getContent() instanceof String)) {
				mp = (Multipart) b.getContent();
				for (int x = 0; x < mp.getCount(); x++) {
					BodyPart bodyPart = mp.getBodyPart(x);
					String a = getText(bodyPart);
					if (a.contains("<!DOCTYPE") || a.contains("<html"))
						break;
					writer.println(a);
				}

			} else {
				writer.println(b.getContent());
			}

			writer.close();
		}

	}

	/**
	 * Método que envia um email
	 * 
	 * @param to      String(destinatário)
	 * @param from    String(remetente)
	 * @param subject String(assunto)
	 * @param text    String(texto)
	 */
	public void sendEmail(String to, String from, String subject, String text) {

		try {

			// Create a default MimeMessage object.
			Message message = new MimeMessage(session);
			// Set From: header field of the header.
			message.setFrom(new InternetAddress(from));

			// Set To: header field of the header.
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));

			// Set Subject: header field
			message.setSubject(subject);

			// Now set the actual message
			message.setText(text);

			// Send message
			Transport.send(message);

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}

	}

	/**
	 * Método que devolve o nome do user
	 * 
	 * @return userName String(user)
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * Método que devolve um texto e uma mensagem
	 * 
	 * @param p String(texto)
	 * @return
	 * @throws MessagingException
	 * @throws IOException
	 */
	public String getText(Part p) throws MessagingException, IOException {
		if (p.isMimeType("text/*")) {
			String s = (String) p.getContent();
			textIsHTML = p.isMimeType("text/html");
			return s;
		}

		if (p.isMimeType("multipart/alternative")) {

			Multipart mp = (Multipart) p.getContent();
			String text = null;
			for (int i = 0; i < mp.getCount(); i++) {
				Part bp = mp.getBodyPart(i);
				if (bp.isMimeType("text/plain")) {
					if (text == null)
						text = getText(bp);
					continue;
				} else if (bp.isMimeType("text/html")) {
					String s = getText(bp);
					if (s != null)
						return s;
				} else {
					return getText(bp);
				}
			}
			return text;
		} else if (p.isMimeType("multipart/*")) {
			Multipart mp = (Multipart) p.getContent();
			for (int i = 0; i < mp.getCount(); i++) {
				String s = getText(mp.getBodyPart(i));
				if (s != null)
					return s;
			}
		}

		return null;
	}

}
