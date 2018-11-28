package bda;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Properties;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Store;

/**
 * Acede à caixa de entrada de uma conta de email tendo em conta que o username
 * e password corretos são fornecidos
 * 
 * @author Grupo 29
 * @version 2.0
 */
public class FetchEmails {

	private ArrayList<Content> msgs = new ArrayList<Content>();

	/**
	 * Método que devolve um arraylist de Conteúdos
	 * 
	 * @return msgs ArrayList<Content> (mensagens que constam no mail do utilizador)
	 */
	public ArrayList<Content> getMsgs() {
		return msgs;
	}

	/**
	 * Método que confirma se o username e password fornecidos estão corretos e
	 * acede à conta de email
	 * 
	 * @param username String (username do utilizador)
	 * @param password String (password do utilizador)
	 */
	public void checkMail(String username, String password) {
		Properties props = new Properties();
		props.setProperty("mail.store.protocol", "imaps");
		try {
			Session session = Session.getInstance(props, null);
			Store store = session.getStore();
			try {
				store.connect("imap.gmail.com", username, password);
			} catch (Exception e) { // descobrir nome
				System.out.println("invalid user and pass");
				return;
			}
			Folder inbox = store.getFolder("INBOX");
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

}


