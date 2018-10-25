package bda;

import java.util.Properties;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Store;

/**
 * Acede à caixa de entrada de uma conta de email tendo em conta 
 * que o username e password corretos são fornecidos
 * @author Grupo 29
 * @version 1.0
 */
public class FetchEmails {
	
	private Message[] msgs;
 
	/**
	 * Método que devolve um vector de Mensagens
	 * @return Vector (mensagens que constam no mail do utilizador)
	 */
	public Message[] getMsgs() {
		return msgs;
	}

	/**
	 * Método que confirma se o username e password fornecidos estão corretos e acede à conta de email
	 * @param username String (username do utilizador)
	 * @param password String (password do utilizador)
	 */
	public  void checkMail(String username, String password) {
		Properties props = new Properties();
		props.setProperty("mail.store.protocol", "imaps");
		try {
			Session session = Session.getInstance(props, null);
			Store store = session.getStore();
			try {
				store.connect("imap.gmail.com", username, password);
			}catch (Exception e){ //descobrir nome
				System.out.println("invalid user and pass");
				return;
			}
			Folder inbox = store.getFolder("INBOX");
			inbox.open(Folder.READ_ONLY);
			msgs = inbox.getMessages();

		} catch (Exception e) {
			System.out.println("invalid");
			e.printStackTrace();
		}
	}

}

	